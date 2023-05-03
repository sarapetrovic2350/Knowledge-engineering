package com.owl.api.example.dto;

import lombok.Data;

@Data
public class CPUResponseDTO {
    private String cpuName;
    private int cpuCores;
    private double cpuClockRate;
    private boolean cpuGaming;
    private int cpuTDP;
    private int cpuThreads;
    private String cpuType;
    private boolean cpuIntegratedGPU;
}
