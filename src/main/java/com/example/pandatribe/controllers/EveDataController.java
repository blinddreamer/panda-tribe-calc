package com.example.pandatribe.controllers;


import com.example.pandatribe.models.dtos.BlueprintDto;
import com.example.pandatribe.models.dtos.SearchDto;
import com.example.pandatribe.services.contracts.BlueprintService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@AllArgsConstructor
public class EveDataController {
    private final BlueprintService blueprintService;

    @PostMapping("/api/v1/type")
    @Cacheable("cacheCalculator")
    public ResponseEntity<?> getEveType(@RequestBody SearchDto searchDto){
        BlueprintDto blueprintDto = blueprintService.getBlueprintData(searchDto);
        if(Objects.isNull(blueprintDto)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blueprint not found");
        }
        return ResponseEntity.ok(blueprintDto);
    }
}
