package com.mottu.mottuguard.service;

import com.mottu.mottuguard.dto.uwb.*;
import com.mottu.mottuguard.models.*;
import com.mottu.mottuguard.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class UwbService {

    @Autowired
    private UwbTagRepo uwbTagRepository;

    @Autowired
    private UwbAnchorRepo uwbAnchorRepository;

    @Autowired
    private UwbMeasurementRepo uwbMeasurementRepository;

    @Autowired
    private MotoRepo motoRepository;

    // UWB Tag methods
    public Page<UwbTagResponse> getAllTags(Pageable pageable) {
        return uwbTagRepository.findAll(pageable).map(this::convertTagToResponse);
    }

    public UwbTagResponse getTagById(Long id) {
        UwbTag tag = uwbTagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UWB Tag not found with id: " + id));
        return convertTagToResponse(tag);
    }

    @Transactional
    public UwbTagResponse createTag(UwbTagRequest request) {
        UwbTag tag = new UwbTag();
        tag.setEui64(request.getEui64());
        tag.setStatus(request.getStatus());

        if (request.getMotoId() != null) {
            Moto moto = motoRepository.findById(request.getMotoId())
                    .orElseThrow(() -> new RuntimeException("Moto not found with id: " + request.getMotoId()));
            tag.setMoto(moto);
        }

        tag = uwbTagRepository.save(tag);
        return convertTagToResponse(tag);
    }

    @Transactional
    public UwbTagResponse updateTag(Long id, UwbTagRequest request) {
        UwbTag tag = uwbTagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UWB Tag not found with id: " + id));

        tag.setEui64(request.getEui64());
        tag.setStatus(request.getStatus());

        if (request.getMotoId() != null) {
            Moto moto = motoRepository.findById(request.getMotoId())
                    .orElseThrow(() -> new RuntimeException("Moto not found with id: " + request.getMotoId()));
            tag.setMoto(moto);
        } else {
            tag.setMoto(null);
        }

        tag = uwbTagRepository.save(tag);
        return convertTagToResponse(tag);
    }

    @Transactional
    public void deleteTag(Long id) {
        if (!uwbTagRepository.existsById(id)) {
            throw new RuntimeException("UWB Tag not found with id: " + id);
        }
        uwbTagRepository.deleteById(id);
    }

    // UWB Anchor methods
    public Page<UwbAnchorResponse> getAllAnchors(Pageable pageable) {
        return uwbAnchorRepository.findAll(pageable).map(this::convertAnchorToResponse);
    }

    public UwbAnchorResponse getAnchorById(Long id) {
        UwbAnchor anchor = uwbAnchorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UWB Anchor not found with id: " + id));
        return convertAnchorToResponse(anchor);
    }

    @Transactional
    public UwbAnchorResponse createAnchor(UwbAnchorRequest request) {
        UwbAnchor anchor = new UwbAnchor();
        anchor.setName(request.getName());
        anchor.setX(request.getX());
        anchor.setY(request.getY());
        anchor.setZ(request.getZ());

        anchor = uwbAnchorRepository.save(anchor);
        return convertAnchorToResponse(anchor);
    }

    @Transactional
    public UwbAnchorResponse updateAnchor(Long id, UwbAnchorRequest request) {
        UwbAnchor anchor = uwbAnchorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UWB Anchor not found with id: " + id));

        anchor.setName(request.getName());
        anchor.setX(request.getX());
        anchor.setY(request.getY());
        anchor.setZ(request.getZ());

        anchor = uwbAnchorRepository.save(anchor);
        return convertAnchorToResponse(anchor);
    }

    @Transactional
    public void deleteAnchor(Long id) {
        if (!uwbAnchorRepository.existsById(id)) {
            throw new RuntimeException("UWB Anchor not found with id: " + id);
        }
        uwbAnchorRepository.deleteById(id);
    }

    // UWB Measurement methods
    public Page<UwbMeasurementResponse> getAllMeasurements(Pageable pageable) {
        return uwbMeasurementRepository.findAll(pageable).map(this::convertMeasurementToResponse);
    }

    public UwbMeasurementResponse getMeasurementById(Long id) {
        UwbMeasurement measurement = uwbMeasurementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UWB Measurement not found with id: " + id));
        return convertMeasurementToResponse(measurement);
    }

    @Transactional
    public UwbMeasurementResponse createMeasurement(UwbMeasurementRequest request) {
        UwbTag tag = uwbTagRepository.findById(request.getTagId())
                .orElseThrow(() -> new RuntimeException("UWB Tag not found with id: " + request.getTagId()));
        UwbAnchor anchor = uwbAnchorRepository.findById(request.getAnchorId())
                .orElseThrow(() -> new RuntimeException("UWB Anchor not found with id: " + request.getAnchorId()));

        UwbMeasurement measurement = new UwbMeasurement();
        measurement.setUwbTag(tag);
        measurement.setUwbAnchor(anchor);
        measurement.setDistance(request.getDistance());
        measurement.setTimestamp(request.getTimestamp() != null ? request.getTimestamp() : Instant.now());

        measurement = uwbMeasurementRepository.save(measurement);
        return convertMeasurementToResponse(measurement);
    }

    @Transactional
    public void deleteMeasurement(Long id) {
        if (!uwbMeasurementRepository.existsById(id)) {
            throw new RuntimeException("UWB Measurement not found with id: " + id);
        }
        uwbMeasurementRepository.deleteById(id);
    }

    // Conversion methods
    private UwbTagResponse convertTagToResponse(UwbTag tag) {
        UwbTagResponse response = new UwbTagResponse();
        response.setId(tag.getId());
        response.setEui64(tag.getEui64());
        response.setStatus(tag.getStatus());
        if (tag.getMoto() != null) {
            response.setMotoId(tag.getMoto().getId());
            response.setMotoPlaca(tag.getMoto().getPlaca());
        }
        return response;
    }

    private UwbAnchorResponse convertAnchorToResponse(UwbAnchor anchor) {
        UwbAnchorResponse response = new UwbAnchorResponse();
        response.setId(anchor.getId());
        response.setName(anchor.getName());
        response.setX(anchor.getX());
        response.setY(anchor.getY());
        response.setZ(anchor.getZ());
        return response;
    }

    private UwbMeasurementResponse convertMeasurementToResponse(UwbMeasurement measurement) {
        UwbMeasurementResponse response = new UwbMeasurementResponse();
        response.setId(measurement.getId());
        response.setTagId(measurement.getUwbTag().getId());
        response.setTagEui64(measurement.getUwbTag().getEui64());
        response.setAnchorId(measurement.getUwbAnchor().getId());
        response.setAnchorName(measurement.getUwbAnchor().getName());
        response.setDistance(measurement.getDistance());
        response.setRssi(measurement.getRssi());
        response.setTimestamp(measurement.getTimestamp());
        return response;
    }
}
