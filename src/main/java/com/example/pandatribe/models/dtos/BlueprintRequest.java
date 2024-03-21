package com.example.pandatribe.models.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BlueprintRequest {
    private String blueprintName;
    private Integer quantity;
    private Integer blueprintMe;
    private String system;
    private Double facilityTax;
    private Integer buildingRig;
    private Integer building;
    private Integer jobRuns;
}
