package com.mottu.mottuguard.repository;

import com.mottu.mottuguard.models.UwbTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface UwbTagRepo extends JpaRepository<UwbTag,Long> { Optional<UwbTag> findByEui64(String eui64); List<UwbTag> findByMotoIsNull(); }
