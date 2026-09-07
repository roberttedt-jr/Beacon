package com.beacon.repository;

import com.beacon.model.PingLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PingLogRepository extends JpaRepository<PingLog, Long> {
    List<PingLog> findTop50ByMonitorIdOrderByCheckedAtDesc(Long monitorId);
}
