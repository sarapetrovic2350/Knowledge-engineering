package com.owl.api.example.controller;

import com.owl.api.example.dto.CPURequestDTO;
import com.owl.api.example.dto.CPUResponseDTO;
import com.owl.api.example.service.CPUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cpu")
public class CPUController {

    private final CPUService cpuService;
    @Autowired
    public CPUController(CPUService cpuService) {
        this.cpuService = cpuService;
    }

    @GetMapping
    public ResponseEntity<List<CPUResponseDTO>> getAllCPUs() {
        return new ResponseEntity<>(this.cpuService.getAllCPUs(), HttpStatus.OK);
    }

    @GetMapping("/cpus-by-properties")
    public ResponseEntity<List<CPUResponseDTO>> getAllCPUsByProperties(
            @RequestParam(required = false, defaultValue = "0") int minCores,
            @RequestParam(required = false, defaultValue = "0") int maxCores,
            @RequestParam(required = false, defaultValue = "0") int minThreads,
            @RequestParam(required = false, defaultValue = "0") int maxThreads,
            @RequestParam(required = false, defaultValue = "0") double minClockRate,
            @RequestParam(required = false, defaultValue = "0") double maxClockRate) {
        if(minCores > maxCores || minThreads > maxThreads || minClockRate > maxClockRate || minCores < 0
                || minThreads < 0 || minClockRate < 0)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        CPURequestDTO requestDTO = new CPURequestDTO();
        requestDTO.setMinCores(minCores);
        requestDTO.setMaxCores(maxCores);
        requestDTO.setMinThreads(minThreads);
        requestDTO.setMaxThreads(maxThreads);
        requestDTO.setMinClockRate(minClockRate);
        requestDTO.setMaxClockRate(maxClockRate);

        return new ResponseEntity<>(this.cpuService.getCPUSByProperties(requestDTO), HttpStatus.OK);
    }

    @GetMapping("/cpus-upgrades")
    public ResponseEntity<List<CPUResponseDTO>> getAllCPUsUpgrades(
            @RequestParam(required = true) String CPU) {
        return new ResponseEntity<>(this.cpuService.getCPUsUpgrades(CPU), HttpStatus.OK);
    }
}
