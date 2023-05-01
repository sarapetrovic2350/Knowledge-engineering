package com.owl.api.example.dto;

import lombok.Data;

@Data
public class SSDRequestDTO {
    private int minCapacity;
    private int maxCapacity;
    private int minReadSpeed;
    private int maxReadSpeed;
    private int minWriteSpeed;
    private int maxWriteSpeed;
}
