package com.example.pandatribe.models.industry;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CostIndex {
    @JsonProperty(value = "activity")
    private String activity;
    @JsonProperty(value = "cost_index")
    private Double costIndex;
}
