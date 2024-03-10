package com.example.pandatribe.models.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class MaterialDto {
    private String icon;
    private String name;
    private Integer neededQuantity;
    private Integer jobsCount;
    private BigDecimal sellPrice;
    private BigDecimal craftPrice;
    private Boolean subMaterials;
    private Double volume;
}
