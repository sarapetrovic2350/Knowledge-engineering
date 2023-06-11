package com.owl.api.example.controller;

import com.owl.api.example.service.BayesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/bayes")
public class BayesController {
    private final BayesService bayesService;
    @Autowired
    public BayesController(BayesService bayesService) {
        this.bayesService = bayesService;
    }

    @GetMapping("/computerSymptoms")
    public ResponseEntity<?> getAllCausesWithProbabilities(
            @RequestParam(required = true) String computerSymptoms) {
        List<String> symptoms = Arrays.asList(computerSymptoms.split(","));
        try{
            return new ResponseEntity<>(this.bayesService.getAllCausesWithProbabilities(symptoms), HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
