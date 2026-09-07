package com.beacon.controller;

import com.beacon.model.Monitor;
import com.beacon.model.PingLog;
import com.beacon.repository.MonitorRepository;
import com.beacon.repository.PingLogRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monitors")
@CrossOrigin(origins = "*") // Permite peticiones desde Vercel, GitHub Pages o localhost
public class MonitorController {

    private final MonitorRepository monitorRepository;
    private final PingLogRepository pingLogRepository;

    public MonitorController(MonitorRepository monitorRepository, PingLogRepository pingLogRepository) {
        this.monitorRepository = monitorRepository;
        this.pingLogRepository = pingLogRepository;
    }

    // Devuelve todos los monitores (PULSE, ATMOS, etc.) con su estado actual y última latencia
    @GetMapping
    public ResponseEntity<List<Monitor>> getAllMonitors() {
        return ResponseEntity.ok(monitorRepository.findAll());
    }

    // Devuelve los últimos 50 pings de un monitor para pintar las barritas verdes/rojas
    @GetMapping("/{id}/logs")
    public ResponseEntity<List<PingLog>> getMonitorLogs(@PathVariable Long id) {
        return ResponseEntity.ok(pingLogRepository.findTop50ByMonitorIdOrderByCheckedAtDesc(id));
    }

    // Permite añadir una nueva web a vigilar mediante POST
    @PostMapping
    public ResponseEntity<Monitor> addMonitor(@RequestBody Monitor monitor) {
        Monitor saved = monitorRepository.save(monitor);
        return ResponseEntity.ok(saved);
    }
}
