package com.mottu.mottuguard.controller;

import com.mottu.mottuguard.dto.moto.*;
import com.mottu.mottuguard.enums.MotoStatus;
import com.mottu.mottuguard.service.MotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/motos")
@Tag(name = "Motorcycles", description = "Motorcycle management endpoints")
@SecurityRequirement(name = "bearerAuth")
public class MotoController {

    @Autowired
    private MotoService motoService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all motorcycles", description = "Get paginated list of motorcycles with optional status filter")
    public ResponseEntity<Page<MotoResponse>> getAllMotos(
            @RequestParam(required = false) MotoStatus status,
            Pageable pageable) {
        Page<MotoResponse> motos = motoService.getAllMotos(status, pageable);
        return ResponseEntity.ok(motos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get motorcycle by ID", description = "Get single motorcycle by its ID")
    public ResponseEntity<MotoResponse> getMotoById(@PathVariable Long id) {
        MotoResponse moto = motoService.getMotoById(id);
        return ResponseEntity.ok(moto);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create motorcycle", description = "Create a new motorcycle")
    public ResponseEntity<MotoResponse> createMoto(@Valid @RequestBody CreateMotoRequest request) {
        MotoResponse moto = motoService.createMoto(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(moto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update motorcycle", description = "Update an existing motorcycle")
    public ResponseEntity<MotoResponse> updateMoto(@PathVariable Long id, @Valid @RequestBody UpdateMotoRequest request) {
        MotoResponse moto = motoService.updateMoto(id, request);
        return ResponseEntity.ok(moto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete motorcycle", description = "Delete a motorcycle by ID")
    public ResponseEntity<Void> deleteMoto(@PathVariable Long id) {
        motoService.deleteMoto(id);
        return ResponseEntity.noContent().build();
    }
}
