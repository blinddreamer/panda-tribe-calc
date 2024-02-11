package com.example.panda2.services.contracts;

import com.example.panda2.models.dtos.MaterialDto;

import java.util.List;

public interface MaterialService {
    List<MaterialDto> getMaterialsByActivity(Integer blueprintId, Integer quantity, Double discountBR, Integer discountBP, Integer discountB);
}
