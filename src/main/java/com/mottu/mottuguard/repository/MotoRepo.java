package com.mottu.mottuguard.repository;

import com.mottu.mottuguard.enums.MotoStatus;
import com.mottu.mottuguard.models.Moto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MotoRepo extends JpaRepository<Moto,Long> { List<Moto> findByStatus(MotoStatus status); }
