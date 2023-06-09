package com.owl.api.example.controller;

import com.owl.api.example.dto.PurposeDTO;
import com.owl.api.example.service.FuzzyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fuzzy")
public class FuzzyController {
    private final FuzzyService fuzzyService;

    public FuzzyController(FuzzyService fuzzyService) {
        this.fuzzyService = fuzzyService;
    }
    @GetMapping("/purpose")
    public ResponseEntity<PurposeDTO> getPurposeType(
            @RequestParam(required = true) int cpu_cores,
            @RequestParam(required = true) int cpu_threads,
            @RequestParam(required = true) int cpu_tdp,
            @RequestParam(required = true) int ram_capacity_in_gb,
            @RequestParam(required = true) int ram_speed,
            @RequestParam(required = true) double ram_voltage,
            @RequestParam(required = true) int gpu_memory_in_gb,
            @RequestParam(required = true) int gpu_clock_in_mhz,
            @RequestParam(required = true) int psu_output_power,
            @RequestParam(required = true) int hdd_capacity_in_gb) {
        return new ResponseEntity<>(this.fuzzyService.getPurposeType(cpu_cores, cpu_threads, cpu_tdp, ram_capacity_in_gb, ram_speed,
                ram_voltage, gpu_memory_in_gb, gpu_clock_in_mhz, psu_output_power, hdd_capacity_in_gb), HttpStatus.OK);
    }
}
