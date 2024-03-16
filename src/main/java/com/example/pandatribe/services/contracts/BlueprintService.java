package com.example.pandatribe.services.contracts;

import com.example.pandatribe.models.dtos.BlueprintDto;
import com.example.pandatribe.models.dtos.BlueprintRequest;

public interface BlueprintService {
    BlueprintDto getBlueprintData(BlueprintRequest searchDto);
}
