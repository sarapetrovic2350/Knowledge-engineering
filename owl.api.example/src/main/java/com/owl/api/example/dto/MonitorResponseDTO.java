package com.owl.api.example.dto;
import lombok.Data;
@Data
public class MonitorResponseDTO {
    private String name;
    private int refreshmentInHz;
    private String resolution;
    private double diagonal;
    private boolean isCurved;
}
