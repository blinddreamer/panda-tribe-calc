package com.example.pandatribe.models.results;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AppraisalResultEntity {
    private String item;
    private Double volume;
    private Integer quantity;
    private BigDecimal sellOrderPrice;
    private BigDecimal buyOrderPrice;
    private Integer sellOrdersCount;
    private Integer buyOrdersCount;
    private BigDecimal sellOrderAverage;
    private BigDecimal buyOrderAverage;
}
