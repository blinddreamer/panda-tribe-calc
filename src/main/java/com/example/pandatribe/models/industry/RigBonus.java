package com.example.pandatribe.models.industry;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RigBonus {
    private Double materialReduction;
    private Double highSecMultiplier;
    private Double lowSecMultiplier;
    private Double nullSecMultiplier;
}
