package com.example.panda2.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "\"invTypes\"", schema = "evesde")
public class EveType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"typeID\"")
    private Integer typeId;
    @Column(name = "\"groupID\"")
    private Integer groupId;
    @Column(name = "\"typeName\"")
    private String typeName;
    @Column(name = "\"description\"")
    private String description;
    @Column(name = "\"mass\"")
    private Double mass;
    @Column(name = "\"volume\"")
    private Double volume;
    @Column(name = "\"capacity\"")
    private Double capacity;
    @Column(name = "\"portionSize\"")
    private Integer portionSize;
    @Column(name = "\"raceID\"")
    private Integer raceId;
    @Column(name = "\"basePrice\"")
    private BigDecimal basePrice;
    @Column(name = "\"published\"")
    private Boolean published;
    @Column(name = "\"marketGroupID\"")
    private Integer marketGroupId;
    @Column(name = "\"iconID\"")
    private Integer iconId;
    @Column(name = "\"soundID\"")
    private Integer soundId;
    @Column(name = "\"graphicID\"")
    private Integer graphicId;
}
