package com.mottu.mottuguard.services;

import com.mottu.mottuguard.repository.*;
import com.mottu.mottuguard.models.*;
import com.mottu.mottuguard.enums.*;
import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
public class MotoService {
    private final MotoRepo motos; private final UwbTagRepo tags;
    public MotoService(MotoRepo m, UwbTagRepo t){this.motos=m; this.tags=t;}

    public Moto reservar(Long id){
        Moto m = motos.findById(id).orElseThrow();
        if(m.getStatus()==MotoStatus.Reservada) throw new IllegalStateException("Já reservada");
        m.setStatus(MotoStatus.Reservada); return m; // JPA flush
    }
    public Moto liberar(Long id){
        Moto m = motos.findById(id).orElseThrow();
        m.setStatus(MotoStatus.Disponivel); return m;
    }
    public void assignTag(Long motoId, Long tagId){
        Moto m = motos.findById(motoId).orElseThrow();
        UwbTag t = tags.findById(tagId).orElseThrow();
        if(t.getMoto()!=null) throw new IllegalStateException("Tag já vinculada");
        if(m.getUwbTag()!=null) throw new IllegalStateException("Moto já possui tag");
        t.setMoto(m); m.setUwbTag(t);
    }
    public void unassignTag(Long motoId){
        Moto m = motos.findById(motoId).orElseThrow();
        if(m.getUwbTag()==null) return; UwbTag t = m.getUwbTag();
        m.setUwbTag(null); t.setMoto(null);
    }
}
