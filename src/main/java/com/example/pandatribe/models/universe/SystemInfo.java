package com.example.pandatribe.models.universe;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SystemInfo {
    private Integer systemId;
    private Double security;
}
