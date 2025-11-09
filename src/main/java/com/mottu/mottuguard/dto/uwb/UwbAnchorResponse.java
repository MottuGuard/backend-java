package com.mottu.mottuguard.dto.uwb;

public class UwbAnchorResponse {
    private Long id;
    private String name;
    private Double x;
    private Double y;
    private Double z;

    // Constructors
    public UwbAnchorResponse() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getX() { return x; }
    public void setX(Double x) { this.x = x; }
    public Double getY() { return y; }
    public void setY(Double y) { this.y = y; }
    public Double getZ() { return z; }
    public void setZ(Double z) { this.z = z; }
}
