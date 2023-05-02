package com.owl.api.example.dto;

import lombok.Data;

@Data
public class RAMResponseDTO {
    private String name;
    private int capacityInGB;
    private int maxFrequencyInMHz;
    private double voltage;
    private String latency;
    private String type;
    private boolean hasRGB;
}
