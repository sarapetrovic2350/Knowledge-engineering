package com.owl.api.example.controller;

import com.owl.api.example.dto.SSDRequestDTO;
import com.owl.api.example.dto.SSDResponseDTO;
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
@RequestMapping("/api/ssd")
public class SSDController {
    private final SSDService ssdService;
    @Autowired
    public SSDController(SSDService ssdService) {
        this.ssdService = ssdService;
    }

    @GetMapping
    public ResponseEntity<List<SSDResponseDTO>> getAllSSDs() {
        return new ResponseEntity<>(this.ssdService.getAllSSDs(), HttpStatus.OK);
    }
    @GetMapping("/ssds-by-properties")
    public ResponseEntity<List<SSDResponseDTO>> getAllSSDsByProperties(
            @RequestParam(required = false, defaultValue = "0") int minCapacity,
            @RequestParam(required = false, defaultValue = "0") int maxCapacity,
            @RequestParam(required = false, defaultValue = "0") int minReadSpeed,
            @RequestParam(required = false, defaultValue = "0") int maxReadSpeed,
            @RequestParam(required = false, defaultValue = "0") int minWriteSpeed,
            @RequestParam(required = false, defaultValue = "0") int maxWriteSpeed) {
        if(minCapacity > maxCapacity || minReadSpeed > maxReadSpeed || minWriteSpeed > maxWriteSpeed || minCapacity < 0
                || minReadSpeed < 0 || minWriteSpeed < 0)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        SSDRequestDTO requestDTO = new SSDRequestDTO();
        requestDTO.setMinCapacity(minCapacity);
        requestDTO.setMaxCapacity(maxCapacity);
        requestDTO.setMinReadSpeed(minReadSpeed);
        requestDTO.setMaxReadSpeed(maxReadSpeed);
        requestDTO.setMinWriteSpeed(minWriteSpeed);
        requestDTO.setMaxWriteSpeed(maxWriteSpeed);

        return new ResponseEntity<>(this.ssdService.getSSDsByProperties(requestDTO), HttpStatus.OK);
    }

    @GetMapping("/ssds-upgrades")
    public ResponseEntity<List<SSDResponseDTO>> getAllSSDsUpgrades(
            @RequestParam(required = true) String SSD) {
        return new ResponseEntity<>(this.ssdService.getSSDsUpgrades(SSD), HttpStatus.OK);
    }

}
