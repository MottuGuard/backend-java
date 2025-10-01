package com.mottu.mottuguard.models;

import jakarta.persistence.*;

@Entity @Table(name="uwb_anchors")
public class UwbAnchor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, unique=true) private String name;
    @Column(nullable=false) private double x;
    @Column(nullable=false) private double y;
    @Column(nullable=false) private double z;
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getName(){return name;} public void setName(String n){this.name=n;}
    public double getX(){return x;} public void setX(double v){this.x=v;}
    public double getY(){return y;} public void setY(double v){this.y=v;}
    public double getZ(){return z;} public void setZ(double v){this.z=v;}
}
