package com.example.pandatribe.models.requests;

import lombok.Data;

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
    private Integer regionId;
}
