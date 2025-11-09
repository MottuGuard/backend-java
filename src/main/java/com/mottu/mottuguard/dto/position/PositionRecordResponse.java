package com.mottu.mottuguard.dto.position;

import java.time.Instant;

public class PositionRecordResponse {
    private Long id;
    private Long motoId;
    private String motoPlaca;
    private Double x;
    private Double y;
    private Instant timestamp;

    // Constructors
    public PositionRecordResponse() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getMotoId() { return motoId; }
    public void setMotoId(Long motoId) { this.motoId = motoId; }
    public String getMotoPlaca() { return motoPlaca; }
    public void setMotoPlaca(String motoPlaca) { this.motoPlaca = motoPlaca; }
    public Double getX() { return x; }
    public void setX(Double x) { this.x = x; }
    public Double getY() { return y; }
    public void setY(Double y) { this.y = y; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
