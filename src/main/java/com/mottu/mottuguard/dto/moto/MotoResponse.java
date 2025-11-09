package com.mottu.mottuguard.dto.moto;

import com.mottu.mottuguard.enums.ModeloMoto;
import com.mottu.mottuguard.enums.MotoStatus;
import java.time.Instant;

public class MotoResponse {
    private Long id;
    private String chassi;
    private String placa;
    private ModeloMoto modelo;
    private MotoStatus status;
    private Double lastX;
    private Double lastY;
    private Instant lastSeenAt;
    private Long uwbTagId;
    private String uwbTagAddress;
    private Instant createdAt;
    private Instant updatedAt;

    // Constructors
    public MotoResponse() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getChassi() { return chassi; }
    public void setChassi(String chassi) { this.chassi = chassi; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public ModeloMoto getModelo() { return modelo; }
    public void setModelo(ModeloMoto modelo) { this.modelo = modelo; }
    public MotoStatus getStatus() { return status; }
    public void setStatus(MotoStatus status) { this.status = status; }
    public Double getLastX() { return lastX; }
    public void setLastX(Double lastX) { this.lastX = lastX; }
    public Double getLastY() { return lastY; }
    public void setLastY(Double lastY) { this.lastY = lastY; }
    public Instant getLastSeenAt() { return lastSeenAt; }
    public void setLastSeenAt(Instant lastSeenAt) { this.lastSeenAt = lastSeenAt; }
    public Long getUwbTagId() { return uwbTagId; }
    public void setUwbTagId(Long uwbTagId) { this.uwbTagId = uwbTagId; }
    public String getUwbTagAddress() { return uwbTagAddress; }
    public void setUwbTagAddress(String uwbTagAddress) { this.uwbTagAddress = uwbTagAddress; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
