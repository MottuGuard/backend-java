package com.mottu.mottuguard.dto.position;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;

public class PositionRecordRequest {
    @NotNull
    private Long motoId;

    @NotNull
    private Double x;

    @NotNull
    private Double y;

    private Instant timestamp;

    // Constructors
    public PositionRecordRequest() {}

    // Getters and Setters
    public Long getMotoId() { return motoId; }
    public void setMotoId(Long motoId) { this.motoId = motoId; }
    public Double getX() { return x; }
    public void setX(Double x) { this.x = x; }
    public Double getY() { return y; }
    public void setY(Double y) { this.y = y; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
