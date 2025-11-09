package com.mottu.mottuguard.dto.websocket;

import java.time.Instant;
import java.util.Map;

public class EventMessage {
    private String tagId;
    private String eventType;  // motion, geofence, offline
    private Map<String, Object> eventData;
    private Instant timestamp;

    public EventMessage() {}

    public EventMessage(String tagId, String eventType, Map<String, Object> eventData, Instant timestamp) {
        this.tagId = tagId;
        this.eventType = eventType;
        this.eventData = eventData;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public String getTagId() { return tagId; }
    public void setTagId(String tagId) { this.tagId = tagId; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public Map<String, Object> getEventData() { return eventData; }
    public void setEventData(Map<String, Object> eventData) { this.eventData = eventData; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
