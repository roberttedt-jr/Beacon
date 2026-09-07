package com.beacon.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "monitors")
public class Monitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String status = "PENDING"; // UP, DOWN, PENDING

    private Integer lastResponseTimeMs;

    private LocalDateTime lastCheckedAt;

    public Monitor() {}

    public Monitor(String name, String url) {
        this.name = name;
        this.url = url;
        this.status = "PENDING";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getLastResponseTimeMs() { return lastResponseTimeMs; }
    public void setLastResponseTimeMs(Integer lastResponseTimeMs) { this.lastResponseTimeMs = lastResponseTimeMs; }

    public LocalDateTime getLastCheckedAt() { return lastCheckedAt; }
    public void setLastCheckedAt(LocalDateTime lastCheckedAt) { this.lastCheckedAt = lastCheckedAt; }
}
