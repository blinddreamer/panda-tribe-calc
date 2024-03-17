package com.example.pandatribe.models.industry.blueprints;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlueprintActivity {
    private Integer blueprintId;
    private Integer craftQuantity;
    private Integer activityId;
}
