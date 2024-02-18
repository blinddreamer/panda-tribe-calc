package com.example.pandatribe.models.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MaterialDto {
    private String name;
    private Integer neededQuantity;
    private Integer jobsCount;
    private Boolean subMaterials;
}
