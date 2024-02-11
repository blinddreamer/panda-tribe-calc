package com.example.pandatribe.services.contracts;

import com.example.pandatribe.models.dtos.MaterialDto;

import java.util.List;

public interface MaterialService {
    List<MaterialDto> getMaterialsByActivity(Integer blueprintId, Integer quantity, Double discountBR, Integer discountBP, Integer discountB);
}
