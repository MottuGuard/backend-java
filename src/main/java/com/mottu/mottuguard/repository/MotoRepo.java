package com.mottu.mottuguard.repository;

import com.mottu.mottuguard.enums.MotoStatus;
import com.mottu.mottuguard.models.Moto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MotoRepo extends JpaRepository<Moto,Long> {
    List<Moto> findByStatus(MotoStatus status);
    Page<Moto> findByStatus(MotoStatus status, Pageable pageable);
    Optional<Moto> findByPlaca(String placa);
    Optional<Moto> findByChassi(String chassi);
}
