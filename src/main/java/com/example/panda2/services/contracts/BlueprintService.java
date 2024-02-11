package com.example.panda2.services.contracts;

import com.example.panda2.models.dtos.BlueprintDto;
import com.example.panda2.models.dtos.SearchDto;

public interface BlueprintService {
    BlueprintDto generateConstructionData(SearchDto searchDto);
}
