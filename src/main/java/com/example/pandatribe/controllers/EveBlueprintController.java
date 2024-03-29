package com.example.pandatribe.controllers;


import com.example.pandatribe.logging.JsonLogger;
import com.example.pandatribe.models.requests.BlueprintRequest;
import com.example.pandatribe.models.results.BlueprintResult;
import com.example.pandatribe.models.results.GetBlueprintsResult;
import com.example.pandatribe.models.results.SystemName;
import com.example.pandatribe.models.universe.Region;
import com.example.pandatribe.models.universe.Station;
import com.example.pandatribe.services.contracts.BlueprintService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/")
public class EveBlueprintController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EveBlueprintController.class);
    private final JsonLogger jsonLogger;
    private final BlueprintService blueprintService;

    @PostMapping("type")
    @Cacheable("cacheCalculator")
    public ResponseEntity<?> getEveType(@RequestBody BlueprintRequest blueprintRequest){
        LOGGER.info("Request for blueprint: " + blueprintRequest);
        BlueprintResult blueprintDto = blueprintService.getBlueprintData(blueprintRequest);
        if(Objects.isNull(blueprintDto)){
            LOGGER.info("Blueprint {} not found", blueprintRequest.getBlueprintName());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blueprint not found");
        }
        jsonLogger.println(blueprintDto);
        return ResponseEntity.ok(blueprintDto);
    }

    @GetMapping("systems")
    @Cacheable("systemNames")
    public ResponseEntity<List<SystemName>> getEveSystems(){
        LOGGER.info("Request for systems received");
        return ResponseEntity.ok(blueprintService.getEveSystems());
    }
    @GetMapping("blueprints")
    @Cacheable("blueprints")
    public ResponseEntity<GetBlueprintsResult> getEveBlueprints(){
        LOGGER.info("Request for blueprints received");
        return ResponseEntity.ok(blueprintService.getEveBlueprints());
    }

    @GetMapping("regions")
    @Cacheable("regions")
    public ResponseEntity<List<Region>> getEveRegions(){
        LOGGER.info("Request for regions received.");
        return ResponseEntity.ok(blueprintService.getEveRegions());
    }

    @GetMapping("stations")
    @Cacheable("stations")
    public ResponseEntity<List<Station>> getEveStations(){
        LOGGER.info("Request for stations received.");
        return ResponseEntity.ok(blueprintService.getEveStations());
    }
}
