package com.example.pandatribe.models.market;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class MarketPriceData {
    @JsonProperty(value = "adjusted_price")
    BigDecimal adjustedPrice;
    @JsonProperty(value = "average_price")
    BigDecimal averagePrice;
    @JsonProperty(value = "type_id")
    Integer typeId;
}
