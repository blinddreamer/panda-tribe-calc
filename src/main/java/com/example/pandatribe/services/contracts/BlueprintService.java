package com.example.pandatribe.services.contracts;

import com.example.pandatribe.models.requests.BlueprintRequest;
import com.example.pandatribe.models.results.*;
import com.example.pandatribe.models.universe.Region;
import com.example.pandatribe.models.universe.Station;

import java.util.List;

public interface BlueprintService {
    BlueprintResult getBlueprintData(BlueprintRequest searchDto);
    GetBlueprintsResult getEveBlueprints();
    List<SystemName> getEveSystems();
    List<Region> getEveRegions();

    List<Station> getEveStations();
}
