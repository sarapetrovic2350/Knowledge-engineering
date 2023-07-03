package com.owl.api.example.dto;

import lombok.Data;
import ucm.gaia.jcolibri.cbrcore.Attribute;
import ucm.gaia.jcolibri.cbrcore.CaseComponent;

@Data
public class SimilarityDTO implements CaseComponent {
    private String cpuName;
    private int cpuCores;
    private String motherboardName;
    private int ramCapacity;
    private String ramType;
    private int graphicCardMemory;
    private int hddCapacity;
    private int psuOutputPower;
    private Attribute idAttribute;

    @Override
    public Attribute getIdAttribute() {
        return null;
    }
}
