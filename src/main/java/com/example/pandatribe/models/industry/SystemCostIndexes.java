package com.example.pandatribe.models.industry;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SystemCostIndexes {
    @JsonProperty(value = "solar_system_id")
    private Integer systemId;
    @JsonProperty(value = "cost_indices")
    private List<CostIndex> costIndexes;
}
