package com.mottu.mottuguard.models;

import jakarta.persistence.*;
import java.time.*;

@Entity @Table(name="uwb_measurements")
public class UwbMeasurement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name="tag_id", nullable=false)
    private UwbTag uwbTag;
    @ManyToOne @JoinColumn(name="anchor_id", nullable=false)
    private UwbAnchor uwbAnchor;
    @Column(nullable=false) private Instant timestamp;
    @Column(nullable=false) private double distance;
    @Column(nullable=false) private double rssi;
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public UwbTag getUwbTag(){return uwbTag;} public void setUwbTag(UwbTag t){this.uwbTag=t;}
    public UwbAnchor getUwbAnchor(){return uwbAnchor;} public void setUwbAnchor(UwbAnchor a){this.uwbAnchor=a;}
    public Instant getTimestamp(){return timestamp;} public void setTimestamp(Instant i){this.timestamp=i;}
    public double getDistance(){return distance;} public void setDistance(double d){this.distance=d;}
    public double getRssi(){return rssi;} public void setRssi(double r){this.rssi=r;}
}
