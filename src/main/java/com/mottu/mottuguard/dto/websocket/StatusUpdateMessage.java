package com.mottu.mottuguard.dto.websocket;

import java.time.Instant;

public class StatusUpdateMessage {
    private String tagId;
    private String status;
    private Instant timestamp;

    public StatusUpdateMessage() {}

    public StatusUpdateMessage(String tagId, String status, Instant timestamp) {
        this.tagId = tagId;
        this.status = status;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public String getTagId() { return tagId; }
    public void setTagId(String tagId) { this.tagId = tagId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
