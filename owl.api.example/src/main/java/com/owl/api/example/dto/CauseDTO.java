package com.owl.api.example.dto;

import lombok.Data;

@Data
public class CauseDTO implements Comparable<CauseDTO> {
    private String cause;
    private double probability;

    @Override
    public int compareTo(CauseDTO otherCause) {
        return Double.compare( otherCause.getProbability(), this.probability);
    }
}
