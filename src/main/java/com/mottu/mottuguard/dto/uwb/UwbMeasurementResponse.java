package com.mottu.mottuguard.dto.uwb;

import java.time.Instant;

public class UwbMeasurementResponse {
    private Long id;
    private Long tagId;
    private String tagEui64;
    private Long anchorId;
    private String anchorName;
    private Double distance;
    private Double rssi;
    private Instant timestamp;

    // Constructors
    public UwbMeasurementResponse() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getTagId() { return tagId; }
    public void setTagId(Long tagId) { this.tagId = tagId; }
    public String getTagEui64() { return tagEui64; }
    public void setTagEui64(String tagEui64) { this.tagEui64 = tagEui64; }
    public Long getAnchorId() { return anchorId; }
    public void setAnchorId(Long anchorId) { this.anchorId = anchorId; }
    public String getAnchorName() { return anchorName; }
    public void setAnchorName(String anchorName) { this.anchorName = anchorName; }
    public Double getDistance() { return distance; }
    public void setDistance(Double distance) { this.distance = distance; }
    public Double getRssi() { return rssi; }
    public void setRssi(Double rssi) { this.rssi = rssi; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
