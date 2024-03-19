package com.example.pandatribe.models.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetBlueprintsResult {
    List<Blueprint> blueprints;
}
