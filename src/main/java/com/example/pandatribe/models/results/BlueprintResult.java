package com.example.pandatribe.models.results;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class BlueprintResult {
    private Integer id;
    private String name;
    private Integer quantity;
    private Integer jobsCount;
    private String icon;
    private BigDecimal craftPrice;
    private BigDecimal sellPrice;
    private List<BlueprintResult> materialsList;
    private BigDecimal adjustedPrice;
    private Double volume;
    private Boolean isCreatable;
    private BigDecimal industryCosts;
    private Integer activityId;
    private Double craftQuantity;
    private Boolean isFuel;
}
