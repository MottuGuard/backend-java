package com.mottu.mottuguard.web.dto;

import com.mottu.mottuguard.enums.*;
import jakarta.validation.constraints.*;
public class MotoForm {
    private Long id;
    @Size(max=64) private String chassi;
    @Size(max=16) private String placa;
    @NotNull private ModeloMoto modelo;
    @NotNull private MotoStatus status;
    // getters/setters…
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getChassi(){return chassi;} public void setChassi(String c){this.chassi=c;}
    public String getPlaca(){return placa;} public void setPlaca(String p){this.placa=p;}
    public ModeloMoto getModelo(){return modelo;} public void setModelo(ModeloMoto m){this.modelo=m;}
    public MotoStatus getStatus(){return status;} public void setStatus(MotoStatus s){this.status=s;}
}
