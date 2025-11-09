package com.mottu.mottuguard.service;

import com.mottu.mottuguard.dto.moto.*;
import com.mottu.mottuguard.enums.MotoStatus;
import com.mottu.mottuguard.models.Moto;
import com.mottu.mottuguard.repository.MotoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MotoService {

    @Autowired
    private MotoRepo motoRepository;

    public Page<MotoResponse> getAllMotos(MotoStatus status, Pageable pageable) {
        Page<Moto> motos;
        if (status != null) {
            motos = motoRepository.findByStatus(status, pageable);
        } else {
            motos = motoRepository.findAll(pageable);
        }
        return motos.map(this::convertToResponse);
    }

    public MotoResponse getMotoById(Long id) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto not found with id: " + id));
        return convertToResponse(moto);
    }

    @Transactional
    public MotoResponse createMoto(CreateMotoRequest request) {
        // Check for duplicate placa or chassi
        if (request.getPlaca() != null && motoRepository.findByPlaca(request.getPlaca()).isPresent()) {
            throw new RuntimeException("Moto with placa " + request.getPlaca() + " already exists");
        }
        if (request.getChassi() != null && motoRepository.findByChassi(request.getChassi()).isPresent()) {
            throw new RuntimeException("Moto with chassi " + request.getChassi() + " already exists");
        }

        Moto moto = new Moto();
        moto.setChassi(request.getChassi());
        moto.setPlaca(request.getPlaca());
        moto.setModelo(request.getModelo());
        moto.setStatus(request.getStatus());

        moto = motoRepository.save(moto);
        return convertToResponse(moto);
    }

    @Transactional
    public MotoResponse updateMoto(Long id, UpdateMotoRequest request) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto not found with id: " + id));

        // Check for duplicate placa
        if (request.getPlaca() != null && !request.getPlaca().equals(moto.getPlaca())) {
            if (motoRepository.findByPlaca(request.getPlaca()).isPresent()) {
                throw new RuntimeException("Moto with placa " + request.getPlaca() + " already exists");
            }
            moto.setPlaca(request.getPlaca());
        }

        // Check for duplicate chassi
        if (request.getChassi() != null && !request.getChassi().equals(moto.getChassi())) {
            if (motoRepository.findByChassi(request.getChassi()).isPresent()) {
                throw new RuntimeException("Moto with chassi " + request.getChassi() + " already exists");
            }
            moto.setChassi(request.getChassi());
        }

        if (request.getModelo() != null) {
            moto.setModelo(request.getModelo());
        }
        if (request.getStatus() != null) {
            moto.setStatus(request.getStatus());
        }

        moto = motoRepository.save(moto);
        return convertToResponse(moto);
    }

    @Transactional
    public void deleteMoto(Long id) {
        if (!motoRepository.existsById(id)) {
            throw new RuntimeException("Moto not found with id: " + id);
        }
        motoRepository.deleteById(id);
    }

    private MotoResponse convertToResponse(Moto moto) {
        MotoResponse response = new MotoResponse();
        response.setId(moto.getId());
        response.setChassi(moto.getChassi());
        response.setPlaca(moto.getPlaca());
        response.setModelo(moto.getModelo());
        response.setStatus(moto.getStatus());
        response.setLastX(moto.getLastX());
        response.setLastY(moto.getLastY());
        response.setLastSeenAt(moto.getLastSeenAt());
        response.setCreatedAt(moto.getCreatedAt());
        response.setUpdatedAt(moto.getUpdatedAt());

        if (moto.getUwbTag() != null) {
            response.setUwbTagId(moto.getUwbTag().getId());
            response.setUwbTagAddress(moto.getUwbTag().getEui64());
        }

        return response;
    }
}
