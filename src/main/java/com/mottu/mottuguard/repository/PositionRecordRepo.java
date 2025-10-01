package com.mottu.mottuguard.repository;

import com.mottu.mottuguard.models.PositionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PositionRecordRepo extends JpaRepository<PositionRecord,Long> { List<PositionRecord> findByMotoIdOrderByTimestampAsc(Long motoId); }
