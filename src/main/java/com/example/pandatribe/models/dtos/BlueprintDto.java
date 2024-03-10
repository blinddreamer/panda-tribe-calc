package com.example.pandatribe.models.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class BlueprintDto {
    private String blueprintName;
    private Integer quantity;
    private String icon;
    private BigDecimal craftPrice;
    private BigDecimal sellPrice;
    private List<MaterialDto> materialsList;
}
