package com.mottu.mottuguard.repository;

import com.mottu.mottuguard.models.PositionRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface PositionRecordRepo extends JpaRepository<PositionRecord,Long> {
    List<PositionRecord> findByMotoIdOrderByTimestampAsc(Long motoId);
    Page<PositionRecord> findByTimestampBetween(Instant start, Instant end, Pageable pageable);
}
