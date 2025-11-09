package com.mottu.mottuguard.dto.websocket;

import java.time.Instant;

public class PositionUpdateMessage {
    private String tagId;
    private Double x;
    private Double y;
    private Instant timestamp;

    public PositionUpdateMessage() {}

    public PositionUpdateMessage(String tagId, Double x, Double y, Instant timestamp) {
        this.tagId = tagId;
        this.x = x;
        this.y = y;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public String getTagId() { return tagId; }
    public void setTagId(String tagId) { this.tagId = tagId; }
    public Double getX() { return x; }
    public void setX(Double x) { this.x = x; }
    public Double getY() { return y; }
    public void setY(Double y) { this.y = y; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
