package com.mottu.mottuguard.web.dto;

import jakarta.validation.constraints.*;
public class AssignTagForm {
    @NotNull private Long motoId;
    @NotNull private Long tagId; // tag disponível
    public Long getMotoId(){return motoId;} public void setMotoId(Long v){this.motoId=v;}
    public Long getTagId(){return tagId;} public void setTagId(Long v){this.tagId=v;}
}
