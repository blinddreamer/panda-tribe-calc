package com.example.pandatribe.repositories.interfaces;

import java.util.List;

public interface EveCustomRepository {
    List<Object[]> getBluePrintCraftQuantity(Integer blueprintId);
}
