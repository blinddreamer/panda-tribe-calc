package com.example.pandatribe.services.contracts;

import com.example.pandatribe.models.dtos.BlueprintResult;

import java.util.List;

public interface MaterialService {
    List<BlueprintResult> getMaterialsByActivity(Integer blueprintId, Integer quantity, Integer discountBR, Integer materialEfficiency, Integer discountB,
                                                 Double security, Integer blueprintCount);
}
