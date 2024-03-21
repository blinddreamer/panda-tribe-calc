package com.example.pandatribe.models.universe;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Region {
    private Integer regionId;
    private String regionName;
}
