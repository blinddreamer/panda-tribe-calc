package com.example.pandatribe.models.requests;

import lombok.Data;

@Data
public class BlueprintRequest {
    private String blueprintName;
    private Integer runs;
    private Integer blueprintMe;
    private String system;
    private Double facilityTax;
    private Integer buildingRig;
    private Integer building;
    private Integer count;
    private Integer regionId;
    private Boolean init;
}
