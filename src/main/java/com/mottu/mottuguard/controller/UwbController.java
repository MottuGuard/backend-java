package com.mottu.mottuguard.controller;

import com.mottu.mottuguard.dto.uwb.*;
import com.mottu.mottuguard.service.UwbService;
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
@RequestMapping("/api/v1")
@SecurityRequirement(name = "bearerAuth")
public class UwbController {

    @Autowired
    private UwbService uwbService;

    // UWB Tags endpoints
    @GetMapping("/uwb-tags")
    @PreAuthorize("isAuthenticated()")
    @Tag(name = "UWB Tags", description = "UWB tag management")
    @Operation(summary = "Get all UWB tags", description = "Get paginated list of UWB tags")
    public ResponseEntity<Page<UwbTagResponse>> getAllTags(Pageable pageable) {
        Page<UwbTagResponse> tags = uwbService.getAllTags(pageable);
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/uwb-tags/{id}")
    @PreAuthorize("isAuthenticated()")
    @Tag(name = "UWB Tags")
    @Operation(summary = "Get UWB tag by ID")
    public ResponseEntity<UwbTagResponse> getTagById(@PathVariable Long id) {
        UwbTagResponse tag = uwbService.getTagById(id);
        return ResponseEntity.ok(tag);
    }

    @PostMapping("/uwb-tags")
    @PreAuthorize("isAuthenticated()")
    @Tag(name = "UWB Tags")
    @Operation(summary = "Create UWB tag")
    public ResponseEntity<UwbTagResponse> createTag(@Valid @RequestBody UwbTagRequest request) {
        UwbTagResponse tag = uwbService.createTag(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(tag);
    }

    @PutMapping("/uwb-tags/{id}")
    @PreAuthorize("isAuthenticated()")
    @Tag(name = "UWB Tags")
    @Operation(summary = "Update UWB tag")
    public ResponseEntity<UwbTagResponse> updateTag(@PathVariable Long id, @Valid @RequestBody UwbTagRequest request) {
        UwbTagResponse tag = uwbService.updateTag(id, request);
        return ResponseEntity.ok(tag);
    }

    @DeleteMapping("/uwb-tags/{id}")
    @PreAuthorize("isAuthenticated()")
    @Tag(name = "UWB Tags")
    @Operation(summary = "Delete UWB tag")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        uwbService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }

    // UWB Anchors endpoints
    @GetMapping("/uwb-anchors")
    @PreAuthorize("isAuthenticated()")
    @Tag(name = "UWB Anchors", description = "UWB anchor management")
    @Operation(summary = "Get all UWB anchors", description = "Get paginated list of UWB anchors")
    public ResponseEntity<Page<UwbAnchorResponse>> getAllAnchors(Pageable pageable) {
        Page<UwbAnchorResponse> anchors = uwbService.getAllAnchors(pageable);
        return ResponseEntity.ok(anchors);
    }

    @GetMapping("/uwb-anchors/{id}")
    @PreAuthorize("isAuthenticated()")
    @Tag(name = "UWB Anchors")
    @Operation(summary = "Get UWB anchor by ID")
    public ResponseEntity<UwbAnchorResponse> getAnchorById(@PathVariable Long id) {
        UwbAnchorResponse anchor = uwbService.getAnchorById(id);
        return ResponseEntity.ok(anchor);
    }

    @PostMapping("/uwb-anchors")
    @PreAuthorize("isAuthenticated()")
    @Tag(name = "UWB Anchors")
    @Operation(summary = "Create UWB anchor")
    public ResponseEntity<UwbAnchorResponse> createAnchor(@Valid @RequestBody UwbAnchorRequest request) {
        UwbAnchorResponse anchor = uwbService.createAnchor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(anchor);
    }

    @PutMapping("/uwb-anchors/{id}")
    @PreAuthorize("isAuthenticated()")
    @Tag(name = "UWB Anchors")
    @Operation(summary = "Update UWB anchor")
    public ResponseEntity<UwbAnchorResponse> updateAnchor(@PathVariable Long id, @Valid @RequestBody UwbAnchorRequest request) {
        UwbAnchorResponse anchor = uwbService.updateAnchor(id, request);
        return ResponseEntity.ok(anchor);
    }

    @DeleteMapping("/uwb-anchors/{id}")
    @PreAuthorize("isAuthenticated()")
    @Tag(name = "UWB Anchors")
    @Operation(summary = "Delete UWB anchor")
    public ResponseEntity<Void> deleteAnchor(@PathVariable Long id) {
        uwbService.deleteAnchor(id);
        return ResponseEntity.noContent().build();
    }

    // UWB Measurements endpoints
    @GetMapping("/uwb-measurements")
    @PreAuthorize("isAuthenticated()")
    @Tag(name = "UWB Measurements", description = "UWB measurement management")
    @Operation(summary = "Get all UWB measurements", description = "Get paginated list of UWB measurements")
    public ResponseEntity<Page<UwbMeasurementResponse>> getAllMeasurements(Pageable pageable) {
        Page<UwbMeasurementResponse> measurements = uwbService.getAllMeasurements(pageable);
        return ResponseEntity.ok(measurements);
    }

    @GetMapping("/uwb-measurements/{id}")
    @PreAuthorize("isAuthenticated()")
    @Tag(name = "UWB Measurements")
    @Operation(summary = "Get UWB measurement by ID")
    public ResponseEntity<UwbMeasurementResponse> getMeasurementById(@PathVariable Long id) {
        UwbMeasurementResponse measurement = uwbService.getMeasurementById(id);
        return ResponseEntity.ok(measurement);
    }

    @PostMapping("/uwb-measurements")
    @PreAuthorize("isAuthenticated()")
    @Tag(name = "UWB Measurements")
    @Operation(summary = "Create UWB measurement")
    public ResponseEntity<UwbMeasurementResponse> createMeasurement(@Valid @RequestBody UwbMeasurementRequest request) {
        UwbMeasurementResponse measurement = uwbService.createMeasurement(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(measurement);
    }

    @DeleteMapping("/uwb-measurements/{id}")
    @PreAuthorize("isAuthenticated()")
    @Tag(name = "UWB Measurements")
    @Operation(summary = "Delete UWB measurement")
    public ResponseEntity<Void> deleteMeasurement(@PathVariable Long id) {
        uwbService.deleteMeasurement(id);
        return ResponseEntity.noContent().build();
    }
}
