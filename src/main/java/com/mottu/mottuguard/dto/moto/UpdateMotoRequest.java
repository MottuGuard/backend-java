package com.mottu.mottuguard.dto.moto;

import com.mottu.mottuguard.enums.ModeloMoto;
import com.mottu.mottuguard.enums.MotoStatus;
import jakarta.validation.constraints.Size;

public class UpdateMotoRequest {
    @Size(max = 64)
    private String chassi;

    @Size(max = 16)
    private String placa;

    private ModeloMoto modelo;
    private MotoStatus status;

    // Constructors
    public UpdateMotoRequest() {}

    public UpdateMotoRequest(String chassi, String placa, ModeloMoto modelo, MotoStatus status) {
        this.chassi = chassi;
        this.placa = placa;
        this.modelo = modelo;
        this.status = status;
    }

    // Getters and Setters
    public String getChassi() { return chassi; }
    public void setChassi(String chassi) { this.chassi = chassi; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public ModeloMoto getModelo() { return modelo; }
    public void setModelo(ModeloMoto modelo) { this.modelo = modelo; }
    public MotoStatus getStatus() { return status; }
    public void setStatus(MotoStatus status) { this.status = status; }
}
