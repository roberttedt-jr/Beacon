package com.beacon.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ping_logs")
public class PingLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monitor_id", nullable = false)
    private Monitor monitor;

    private Integer statusCode;

    private Integer responseTimeMs;

    private Boolean isUp;

    private LocalDateTime checkedAt;

    public PingLog() {}

    public PingLog(Monitor monitor, Integer statusCode, Integer responseTimeMs, Boolean isUp) {
        this.monitor = monitor;
        this.statusCode = statusCode;
        this.responseTimeMs = responseTimeMs;
        this.isUp = isUp;
        this.checkedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Monitor getMonitor() { return monitor; }
    public void setMonitor(Monitor monitor) { this.monitor = monitor; }

    public Integer getStatusCode() { return statusCode; }
    public void setStatusCode(Integer statusCode) { this.statusCode = statusCode; }

    public Integer getResponseTimeMs() { return responseTimeMs; }
    public void setResponseTimeMs(Integer responseTimeMs) { this.responseTimeMs = responseTimeMs; }

    public Boolean getIsUp() { return isUp; }
    public void setIsUp(Boolean isUp) { this.isUp = isUp; }

    public LocalDateTime getCheckedAt() { return checkedAt; }
    public void setCheckedAt(LocalDateTime checkedAt) { this.checkedAt = checkedAt; }
}
