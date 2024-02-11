package com.example.panda2.models.dtos;

import lombok.Data;

@Data
public class SearchDto {
    private String shipName;
    private Integer quantity;
    private Integer blueprintMe;
    private Integer blueprintTe;
    private Double buildingRig;
    private Integer building;
}
