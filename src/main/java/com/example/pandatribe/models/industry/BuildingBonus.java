package com.example.pandatribe.models.industry;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BuildingBonus {
    private Integer materialReduction;
    private Integer costReduction;
}
