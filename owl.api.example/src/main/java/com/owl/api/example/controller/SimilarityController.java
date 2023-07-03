package com.owl.api.example.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.owl.api.example.dto.SimilarityEvaluationDTO;
import com.owl.api.example.service.SimilarityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.io.IOException;

@RestController
@RequestMapping("/api/similarity")
public class SimilarityController {
    private final SimilarityService similarityService;
    private final ObjectMapper objectMapper;
    @Autowired
    public SimilarityController(SimilarityService similarityService, ObjectMapper objectMapper) {
        this.similarityService = similarityService;
        this.objectMapper = objectMapper;
    }


    @GetMapping("/getSimilarComputers")
    public ResponseEntity<List<SimilarityEvaluationDTO>> getSimilarComputers(
            @RequestParam(required = true) String cpuName,
            @RequestParam(required = true) int cpuCores,
            @RequestParam(required = true) String motherboardName,
            @RequestParam(required = true) int ramCapacity,
            @RequestParam(required = true) String ramType,
            @RequestParam(required = true) int graphicCardMemory,
            @RequestParam(required = true) int hddCapacity,
            @RequestParam(required = true) int psuOutputPower) {
        this.similarityService.getSimilarComputers(cpuName, cpuCores, motherboardName, ramCapacity, ramType, graphicCardMemory,
                hddCapacity, psuOutputPower);
        return new ResponseEntity<>(getResultsFromTXTFile(), HttpStatus.OK);
    }
    private List<SimilarityEvaluationDTO> getResultsFromTXTFile() {
        List<SimilarityEvaluationDTO> results;
        try (BufferedReader reader = new BufferedReader(new FileReader("data/results.txt"))) {
            String jsonString = reader.readLine();
            results = objectMapper.readValue(jsonString, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return results;
    }

}
