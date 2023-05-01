package com.owl.api.example.dto;

import lombok.Data;

@Data
public class SSDResponseDTO {
    private String name;
    private int capacityInGB;
    private int readSpeedInMBs;
    private int writeSpeedInMBs;
    private String interfaceSSD;
    private String format;
}
