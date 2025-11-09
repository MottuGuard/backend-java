package com.mottu.mottuguard.service;

import com.mottu.mottuguard.dto.position.*;
import com.mottu.mottuguard.models.*;
import com.mottu.mottuguard.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class PositionService {

    @Autowired
    private PositionRecordRepo positionRecordRepository;

    @Autowired
    private MotoRepo motoRepository;

    public Page<PositionRecordResponse> getAllPositions(Pageable pageable) {
        return positionRecordRepository.findAll(pageable).map(this::convertToResponse);
    }

    public Page<PositionRecordResponse> getPositionsByTimeRange(Instant start, Instant end, Pageable pageable) {
        return positionRecordRepository.findByTimestampBetween(start, end, pageable).map(this::convertToResponse);
    }

    public PositionRecordResponse getPositionById(Long id) {
        PositionRecord position = positionRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Position record not found with id: " + id));
        return convertToResponse(position);
    }

    @Transactional
    public PositionRecordResponse createPosition(PositionRecordRequest request) {
        Moto moto = motoRepository.findById(request.getMotoId())
                .orElseThrow(() -> new RuntimeException("Moto not found with id: " + request.getMotoId()));

        PositionRecord position = new PositionRecord();
        position.setMoto(moto);
        position.setX(request.getX());
        position.setY(request.getY());
        position.setTimestamp(request.getTimestamp() != null ? request.getTimestamp() : Instant.now());

        // Update moto's last position
        moto.setLastX(request.getX());
        moto.setLastY(request.getY());
        moto.setLastSeenAt(position.getTimestamp());
        motoRepository.save(moto);

        position = positionRecordRepository.save(position);
        return convertToResponse(position);
    }

    @Transactional
    public void deletePosition(Long id) {
        if (!positionRecordRepository.existsById(id)) {
            throw new RuntimeException("Position record not found with id: " + id);
        }
        positionRecordRepository.deleteById(id);
    }

    private PositionRecordResponse convertToResponse(PositionRecord position) {
        PositionRecordResponse response = new PositionRecordResponse();
        response.setId(position.getId());
        response.setMotoId(position.getMoto().getId());
        response.setMotoPlaca(position.getMoto().getPlaca());
        response.setX(position.getX());
        response.setY(position.getY());
        response.setTimestamp(position.getTimestamp());
        return response;
    }
}
