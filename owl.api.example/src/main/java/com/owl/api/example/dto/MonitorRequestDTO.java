package com.owl.api.example.dto;
import lombok.Data;
@Data
public class MonitorRequestDTO {
    private double minDiagonal;
    private double maxDiagonal;
    private int minRefreshment;
    private int maxRefreshment;
    private boolean curved;
}
