package com.example.pandatribe.services.contracts;

import com.example.pandatribe.models.dtos.*;
import com.example.pandatribe.models.universe.SystemInfo;

import java.util.List;

public interface BlueprintService {
    BlueprintResult getBlueprintData(BlueprintRequest searchDto);
    GetBlueprintsResult getEveBlueprints();
    List<SystemName> getEveSystems();
}
