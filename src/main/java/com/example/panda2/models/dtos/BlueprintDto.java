package com.example.panda2.models.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BlueprintDto {
    private String blueprintName;
    private List<MaterialDto> materialsList;
}
