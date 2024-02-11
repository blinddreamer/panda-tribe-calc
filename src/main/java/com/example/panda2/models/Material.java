package com.example.panda2.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "\"industryActivityMaterials\"",schema = "evesde")
public class Material {

    @EmbeddedId
    private MaterialBlueprintKey  blueprintTypeId;

//    @ManyToOne
//    @MapsId("materialTypeId")
//    @JoinColumn(name = "\"materialTypeID\"")
//    private EveType material;
//
//    @ManyToOne
//    @MapsId("typeId")
//    @JoinColumn(name = "\"typeID\"")
//    private EveType blueprint;

    private Integer quantity;
    @Column(name = "\"activityID\"")
    private Integer activity;
}
