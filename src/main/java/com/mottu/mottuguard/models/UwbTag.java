package com.mottu.mottuguard.models;

import com.mottu.mottuguard.enums.TagStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity @Table(name="uwb_tags")
public class UwbTag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Column(unique=true) private String eui64;
    @Enumerated(EnumType.STRING) @Column(nullable=false)
    private TagStatus status = TagStatus.Inativa;

    @OneToOne
    @JoinColumn(name="moto_id", unique=true)
    private Moto moto; // null quando não atribuída

    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getEui64(){return eui64;} public void setEui64(String e){this.eui64=e;}
    public TagStatus getStatus(){return status;} public void setStatus(TagStatus s){this.status=s;}
    public Moto getMoto(){return moto;} public void setMoto(Moto m){this.moto=m;}
}
