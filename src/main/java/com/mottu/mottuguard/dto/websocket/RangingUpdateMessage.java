package com.mottu.mottuguard.dto.websocket;

import java.time.Instant;
import java.util.Map;

public class RangingUpdateMessage {
    private String tagId;
    private Map<String, Double> ranges;  // anchor address -> distance
    private Instant timestamp;

    public RangingUpdateMessage() {}

    public RangingUpdateMessage(String tagId, Map<String, Double> ranges, Instant timestamp) {
        this.tagId = tagId;
        this.ranges = ranges;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public String getTagId() { return tagId; }
    public void setTagId(String tagId) { this.tagId = tagId; }
    public Map<String, Double> getRanges() { return ranges; }
    public void setRanges(Map<String, Double> ranges) { this.ranges = ranges; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
