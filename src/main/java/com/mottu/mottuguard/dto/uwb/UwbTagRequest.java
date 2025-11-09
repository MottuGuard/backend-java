package com.mottu.mottuguard.dto.uwb;

import com.mottu.mottuguard.enums.TagStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UwbTagRequest {
    @NotBlank
    @Size(max = 32)
    private String eui64;

    private TagStatus status = TagStatus.Ativa;
    private Long motoId;

    // Constructors
    public UwbTagRequest() {}

    // Getters and Setters
    public String getEui64() { return eui64; }
    public void setEui64(String eui64) { this.eui64 = eui64; }
    public TagStatus getStatus() { return status; }
    public void setStatus(TagStatus status) { this.status = status; }
    public Long getMotoId() { return motoId; }
    public void setMotoId(Long motoId) { this.motoId = motoId; }
}
