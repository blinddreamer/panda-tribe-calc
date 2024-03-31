package com.example.pandatribe.controllers;

import com.example.pandatribe.logging.JsonLogger;
import com.example.pandatribe.models.requests.AppraisalRequest;
import com.example.pandatribe.models.results.AppraisalResult;
import com.example.pandatribe.services.contracts.AppraisalService;
import com.example.pandatribe.services.contracts.BlueprintService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/")
public class EveAppraisalController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EveBlueprintController.class);
    private final JsonLogger jsonLogger;
    private final AppraisalService appraisalService;

    @PostMapping("appraisal")
    public ResponseEntity<?> getAppraisalPrices(@RequestBody AppraisalRequest request){
        AppraisalResult appraisalResult = appraisalService.generateAppraisalResult(request);
        return ResponseEntity.ok(appraisalResult);
    }

}
