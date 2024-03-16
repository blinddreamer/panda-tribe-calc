package com.example.pandatribe.repositories.interfaces;

import com.example.pandatribe.models.industry.blueprints.BlueprintActivity;
import com.example.pandatribe.models.universe.SystemInfo;

public interface EveCustomRepository {
    BlueprintActivity getBluePrintInfo(Integer blueprintId);
    SystemInfo getSystemId(String systemName);
    Integer getVolume(Integer typeId);
}
