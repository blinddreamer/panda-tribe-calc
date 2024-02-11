package com.example.pandatribe.repositories;

import com.example.pandatribe.repositories.interfaces.EveCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EveCustomRepositoryImpl implements EveCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Object[]> getBluePrintCraftQuantity(Integer blueprintId) {
        String nativeQuery = "SELECT quantity,\"typeID\" FROM evesde.\"industryActivityProducts\" iap WHERE iap.\"productTypeID\" =:blueprintId ";
        return entityManager.createNativeQuery(nativeQuery).setParameter("blueprintId", blueprintId).getResultList();
    }
}
