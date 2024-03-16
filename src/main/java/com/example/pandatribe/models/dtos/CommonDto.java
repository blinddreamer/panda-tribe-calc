package com.example.pandatribe.models.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CommonDto {
    private String name;
    private Integer quantity;
    private Integer jobsCount;
    private String icon;
    private BigDecimal craftPrice;
    private BigDecimal sellPrice;
    private List<MaterialDto> materialsList;
    private BigDecimal adjustedPrice;
    private Boolean subMaterials;
    private Double volume;
}
