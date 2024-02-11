package com.example.pandatribe.services.contracts;

import com.example.pandatribe.models.dtos.BlueprintDto;
import com.example.pandatribe.models.dtos.SearchDto;

public interface BlueprintService {
    BlueprintDto generateConstructionData(SearchDto searchDto);
}
