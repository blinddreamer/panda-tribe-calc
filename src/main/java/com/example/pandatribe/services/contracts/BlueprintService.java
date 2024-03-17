package com.example.pandatribe.services.contracts;

import com.example.pandatribe.models.dtos.BlueprintRequest;
import com.example.pandatribe.models.dtos.BlueprintResult;

public interface BlueprintService {
    BlueprintResult getBlueprintData(BlueprintRequest searchDto);
}
