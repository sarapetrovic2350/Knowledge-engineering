package com.owl.api.example.dto;

import lombok.Data;

@Data
public class RAMRequestDTO {
    private int minCapacity;
    private int maxCapacity;
    private double minVoltage;
    private double maxVoltage;
    private int minFrequency;
    private int maxFrequency;

}
