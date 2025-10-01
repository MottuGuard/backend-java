package com.mottu.mottuguard.models;

import com.mottu.mottuguard.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.*;
import java.util.*;

@Entity @Table(name="motos")
public class Moto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max=64) private String chassi;
    @Size(max=16) private String placa;
    @Enumerated(EnumType.STRING) @Column(nullable=false)
    private ModeloMoto modelo;
    @Enumerated(EnumType.STRING) @Column(nullable=false)
    private MotoStatus status = MotoStatus.Disponivel;

    @Column(name = "last_x")       private Double lastX;
    @Column(name = "last_y")       private Double lastY;
    @Column(name = "last_seen_at") private Instant lastSeenAt;
    @Column(name = "created_at")   private Instant createdAt;
    @Column(name = "updated_at")   private Instant updatedAt;

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = Instant.now();
    }



      // 1:1 com UwbTag (lado inverso)
    @OneToOne(mappedBy = "moto")
    private UwbTag uwbTag;

    @OneToMany(mappedBy = "moto")
    private List<PositionRecord> positionHistory = new ArrayList<>();

    // getters/setters
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getChassi(){return chassi;} public void setChassi(String c){this.chassi=c;}
    public String getPlaca(){return placa;} public void setPlaca(String p){this.placa=p;}
    public ModeloMoto getModelo(){return modelo;} public void setModelo(ModeloMoto m){this.modelo=m;}
    public MotoStatus getStatus(){return status;} public void setStatus(MotoStatus s){this.status=s;}
    public Double getLastX(){return lastX;} public void setLastX(Double v){this.lastX=v;}
    public Double getLastY(){return lastY;} public void setLastY(Double v){this.lastY=v;}
    public Instant getLastSeenAt(){return lastSeenAt;} public void setLastSeenAt(Instant i){this.lastSeenAt=i;}
    public Instant getCreatedAt(){return createdAt;} public void setCreatedAt(Instant i){this.createdAt=i;}
    public Instant getUpdatedAt(){return updatedAt;} public void setUpdatedAt(Instant i){this.updatedAt=i;}
    public UwbTag getUwbTag(){return uwbTag;} public void setUwbTag(UwbTag t){this.uwbTag=t;}
    public List<PositionRecord> getPositionHistory(){return positionHistory;}
}
