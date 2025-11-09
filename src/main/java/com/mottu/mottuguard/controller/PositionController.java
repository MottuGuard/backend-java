package com.mottu.mottuguard.controller;

import com.mottu.mottuguard.dto.position.*;
import com.mottu.mottuguard.service.PositionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/position-records")
@Tag(name = "Position Records", description = "Position record management")
@SecurityRequirement(name = "bearerAuth")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all position records", description = "Get paginated list of position records with optional time range filter")
    public ResponseEntity<Page<PositionRecordResponse>> getAllPositions(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant end,
            Pageable pageable) {

        Page<PositionRecordResponse> positions;
        if (start != null && end != null) {
            positions = positionService.getPositionsByTimeRange(start, end, pageable);
        } else {
            positions = positionService.getAllPositions(pageable);
        }

        return ResponseEntity.ok(positions);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get position record by ID")
    public ResponseEntity<PositionRecordResponse> getPositionById(@PathVariable Long id) {
        PositionRecordResponse position = positionService.getPositionById(id);
        return ResponseEntity.ok(position);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create position record")
    public ResponseEntity<PositionRecordResponse> createPosition(@Valid @RequestBody PositionRecordRequest request) {
        PositionRecordResponse position = positionService.createPosition(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(position);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete position record")
    public ResponseEntity<Void> deletePosition(@PathVariable Long id) {
        positionService.deletePosition(id);
        return ResponseEntity.noContent().build();
    }
}
