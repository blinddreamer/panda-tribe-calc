package com.example.pandatribe.repositories;

import com.example.pandatribe.models.industry.blueprints.BlueprintActivity;
import com.example.pandatribe.models.universe.SystemInfo;
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
    public BlueprintActivity getBluePrintInfo(Integer blueprintId) {
        String nativeQuery = "SELECT quantity,\"typeID\",\"activityID\" FROM evesde.\"industryActivityProducts\" iap WHERE iap.\"productTypeID\" =:blueprintId ";
        List<Object[]> result = entityManager.createNativeQuery(nativeQuery).setParameter("blueprintId", blueprintId).getResultList();
        return result.isEmpty() ? null :
                BlueprintActivity.builder()
                        .blueprintId((Integer) result.get(0)[1])
                        .craftQuantity((Integer) result.get(0)[0])
                        .activityId((Integer) result.get(0)[2])
                        .build();
    }

    public SystemInfo getSystemInfo(String systemName){
        String nativeQuery = "SELECT \"solarSystemID\", \"security\" FROM evesde.\"mapSolarSystems\" mss WHERE mss.\"solarSystemName\" = :systemName";
        List<Object[]> result = entityManager.createNativeQuery(nativeQuery).setParameter("systemName",systemName).getResultList();
        return result.isEmpty() ? null : SystemInfo.builder().systemId((Integer) result.get(0)[0]).security((Double) result.get(0)[1]).build();
    }

    public Integer getVolume(Integer typeId){
        String nativeQuery = "SELECT \"volume\" FROM evesde.\"invVolumes\" iv WHERE iv.\"typeID\" = :typeId";
        List<Object> result = entityManager.createNativeQuery(nativeQuery).setParameter("typeId",typeId).getResultList();
        return result.isEmpty() ? null : (Integer) result.get(0);
    }
}
