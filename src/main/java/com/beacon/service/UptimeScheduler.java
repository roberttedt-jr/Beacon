package com.beacon.service;

import com.beacon.model.Monitor;
import com.beacon.model.PingLog;
import com.beacon.repository.MonitorRepository;
import com.beacon.repository.PingLogRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UptimeScheduler {

    private final MonitorRepository monitorRepository;
    private final PingLogRepository pingLogRepository;
    private final HttpClient httpClient;

    public UptimeScheduler(MonitorRepository monitorRepository, PingLogRepository pingLogRepository) {
        this.monitorRepository = monitorRepository;
        this.pingLogRepository = pingLogRepository;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
    }

    @PostConstruct
    public void initMonitors() {
        if (monitorRepository.count() == 0) {
            monitorRepository.save(new Monitor("PULSE App", "https://pulse-psi-blond.vercel.app/"));
            monitorRepository.save(new Monitor("ATMOS Weather", "https://roberttedt-jr.github.io/ATMOS./app.html"));
        }
    }

    @Scheduled(fixedRate = 60000) // Se ejecuta cada 60 segundos
    public void runHealthChecks() {
        List<Monitor> monitors = monitorRepository.findAll();

        for (Monitor monitor : monitors) {
            long startTime = System.currentTimeMillis();
            boolean isUp = false;
            int statusCode = 0;

            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(monitor.getUrl()))
                        .timeout(Duration.ofSeconds(8))
                        .GET()
                        .build();

                HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
                statusCode = response.statusCode();
                isUp = (statusCode >= 200 && statusCode < 400);
            } catch (Exception e) {
                statusCode = 500;
                isUp = false;
            }

            long duration = System.currentTimeMillis() - startTime;

            monitor.setStatus(isUp ? "UP" : "DOWN");
            monitor.setLastResponseTimeMs((int) duration);
            monitor.setLastCheckedAt(LocalDateTime.now());
            monitorRepository.save(monitor);

            PingLog log = new PingLog(monitor, statusCode, (int) duration, isUp);
            pingLogRepository.save(log);
        }
    }
}
