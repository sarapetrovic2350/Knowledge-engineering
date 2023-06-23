package com.owl.api.example.controller;

import com.owl.api.example.dto.*;
import com.owl.api.example.service.RAMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ram")
public class RAMController {
    private final RAMService ramService;
    @Autowired
    public RAMController(RAMService ramService) {
        this.ramService = ramService;
    }

    @GetMapping
    public ResponseEntity<List<RAMResponseDTO>> getAllRAMs() {
        return new ResponseEntity<>(this.ramService.getAllRAMs(), HttpStatus.OK);
    }

    @GetMapping("/rams-by-properties")
    public ResponseEntity<List<RAMResponseDTO>> getAllRAMsByProperties(
            @RequestParam(required = false, defaultValue = "0") int minCapacity,
            @RequestParam(required = false, defaultValue = "0") int maxCapacity,
            @RequestParam(required = false, defaultValue = "0") int minFrequency,
            @RequestParam(required = false, defaultValue = "0") int maxFrequency,
            @RequestParam(required = false, defaultValue = "0") double minVoltage,
            @RequestParam(required = false, defaultValue = "0") double maxVoltage) {
        if(minCapacity > maxCapacity || minFrequency > maxFrequency || minVoltage > maxVoltage || minCapacity < 0
                || minFrequency < 0 || minVoltage < 0)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        RAMRequestDTO requestDTO = new RAMRequestDTO();
        requestDTO.setMinCapacity(minCapacity);
        requestDTO.setMaxCapacity(maxCapacity);
        requestDTO.setMinFrequency(minFrequency);
        requestDTO.setMaxFrequency(maxFrequency);
        requestDTO.setMinVoltage(minVoltage);
        requestDTO.setMaxVoltage(maxVoltage);

        return new ResponseEntity<>(this.ramService.getRAMsByProperties(requestDTO), HttpStatus.OK);
    }

    @GetMapping("/rams-upgrades")
    public ResponseEntity<List<RAMResponseDTO>> getAllRAMsUpgrades(
            @RequestParam(required = true) String ram,
            @RequestParam(required = true) String motherboard) {
        return new ResponseEntity<>(this.ramService.getRAMsUpgrades(ram, motherboard), HttpStatus.OK);
    }
}
