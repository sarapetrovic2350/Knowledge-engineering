package com.owl.api.example.controller;

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
    public ResponseEntity<Double> getPurposeType(
            @RequestParam(required = true) double service,
            @RequestParam(required = true) double food) {
        return new ResponseEntity<>(this.fuzzyService.getPurposeType(service, food), HttpStatus.OK);
    }
}
