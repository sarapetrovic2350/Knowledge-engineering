package com.owl.api.example.controller;

import com.owl.api.example.dto.MotherboardResponseDTO;
import com.owl.api.example.service.CPUService;
import com.owl.api.example.service.MotherboardService;
import com.owl.api.example.service.RAMService;
import com.owl.api.example.service.SSDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/motherboard")
public class MotherboardController {
    private final MotherboardService motherboardService;
    private final CPUService cpuService;
    private final RAMService ramService;
    private final SSDService ssdService;

    @Autowired
    public MotherboardController(MotherboardService motherboardService, CPUService cpuService, RAMService ramService, SSDService ssdService) {
        this.motherboardService = motherboardService;
        this.cpuService = cpuService;
        this.ramService = ramService;
        this.ssdService = ssdService;
    }

    @GetMapping
    public ResponseEntity<List<MotherboardResponseDTO>> getAllMotherboards() {
        return new ResponseEntity<>(this.motherboardService.getAllMotherboards(), HttpStatus.OK);
    }
    @GetMapping("/upgrades")
    public ResponseEntity<?> getComponent(
            @RequestParam(required = true) String component,
            @RequestParam(required = true) String motherboard,
            @RequestParam(required = true) String cpu,
            @RequestParam(required = true) String ssd,
            @RequestParam(required = true) String ram) {
        if (component.equals("cpu"))
            return new ResponseEntity<>(this.cpuService.getCPUsUpgrades(cpu, motherboard), HttpStatus.OK);
        else if (component.equals("ssd"))
            return new ResponseEntity<>(this.ssdService.getSSDsUpgrades(ssd, motherboard), HttpStatus.OK);
        else if (component.equals("ram"))
            return new ResponseEntity<>(this.ramService.getRAMsUpgrades(ram, motherboard), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
