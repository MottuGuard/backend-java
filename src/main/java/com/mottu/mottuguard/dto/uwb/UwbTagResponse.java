package com.mottu.mottuguard.dto.uwb;

import com.mottu.mottuguard.enums.TagStatus;

public class UwbTagResponse {
    private Long id;
    private String eui64;
    private TagStatus status;
    private Long motoId;
    private String motoPlaca;

    // Constructors
    public UwbTagResponse() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEui64() { return eui64; }
    public void setEui64(String eui64) { this.eui64 = eui64; }
    public TagStatus getStatus() { return status; }
    public void setStatus(TagStatus status) { this.status = status; }
    public Long getMotoId() { return motoId; }
    public void setMotoId(Long motoId) { this.motoId = motoId; }
    public String getMotoPlaca() { return motoPlaca; }
    public void setMotoPlaca(String motoPlaca) { this.motoPlaca = motoPlaca; }
}
