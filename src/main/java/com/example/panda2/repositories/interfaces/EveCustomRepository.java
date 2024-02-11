package com.example.panda2.repositories.interfaces;

import java.util.List;

public interface EveCustomRepository {
    List<Object[]> getBluePrintCraftQuantity(Integer blueprintId);
}
