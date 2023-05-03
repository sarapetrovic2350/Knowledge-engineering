package com.owl.api.example.dto;
import lombok.Data;
@Data
public class CPURequestDTO {
    private int minCores;
    private int maxCores;
    private int minThreads;
    private int maxThreads;
    private double minClockRate;
    private double maxClockRate;
}
