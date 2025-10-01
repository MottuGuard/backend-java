package com.mottu.mottuguard.models;

import jakarta.persistence.*;
import java.time.*;

@Entity @Table(name="position_records")
public class PositionRecord {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name="moto_id", nullable=false)
    private Moto moto;
    @Column(nullable=false) private Instant timestamp;
    @Column(nullable=false) private double x;
    @Column(nullable=false) private double y;
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public Moto getMoto(){return moto;} public void setMoto(Moto m){this.moto=m;}
    public Instant getTimestamp(){return timestamp;} public void setTimestamp(Instant i){this.timestamp=i;}
    public double getX(){return x;} public void setX(double v){this.x=v;}
    public double getY(){return y;} public void setY(double v){this.y=v;}
}
