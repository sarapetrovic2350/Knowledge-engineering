package com.owl.api.example.controller;

import com.owl.api.example.dto.MonitorRequestDTO;
import com.owl.api.example.dto.MonitorResponseDTO;
import com.owl.api.example.service.MonitorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@RestController
@RequestMapping("/api/monitor")
public class MonitorController {
    private final MonitorService monitorService;

    @Autowired
    public MonitorController(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @GetMapping
    public ResponseEntity<List<MonitorResponseDTO>> getAllMonitors() {
        return new ResponseEntity<>(this.monitorService.getAllMonitors(), HttpStatus.OK);
    }

    @GetMapping("/monitors-by-properties")
    public ResponseEntity<List<MonitorResponseDTO>> getAllMonitorsByProperties(
            @RequestParam(required = false, defaultValue = "0") double minDiag,
            @RequestParam(required = false, defaultValue = "0") double maxDiag,
            @RequestParam(required = false, defaultValue = "0") int minRef,
            @RequestParam(required = false, defaultValue = "0") int maxRef,
            @RequestParam(required = false) boolean curved) {
        if(minDiag > maxDiag || minRef > maxRef || minDiag < 0 || maxDiag < 0 || minRef < 0)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        MonitorRequestDTO requestDTO = new MonitorRequestDTO();
        requestDTO.setMinDiagonal(minDiag);
        requestDTO.setMaxDiagonal(maxDiag);
        requestDTO.setMinRefreshment(minRef);
        requestDTO.setMaxRefreshment(maxRef);
        requestDTO.setCurved(curved);
        return new ResponseEntity<>(this.monitorService.getMonitorsByProperties(requestDTO), HttpStatus.OK);
    }

    @GetMapping("/monitors-upgrades")
    public ResponseEntity<List<MonitorResponseDTO>> getAllMonitorsUpgrades(
            @RequestParam(required = true) String monitor) {
        return new ResponseEntity<>(this.monitorService.getMonitorsUpgrades(monitor), HttpStatus.OK);
    }
}
