package com.mottu.mottuguard.dto.uwb;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;

public class UwbMeasurementRequest {
    @NotNull
    private Long tagId;

    @NotNull
    private Long anchorId;

    @NotNull
    private Double distance;

    private Instant timestamp;

    // Constructors
    public UwbMeasurementRequest() {}

    // Getters and Setters
    public Long getTagId() { return tagId; }
    public void setTagId(Long tagId) { this.tagId = tagId; }
    public Long getAnchorId() { return anchorId; }
    public void setAnchorId(Long anchorId) { this.anchorId = anchorId; }
    public Double getDistance() { return distance; }
    public void setDistance(Double distance) { this.distance = distance; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
