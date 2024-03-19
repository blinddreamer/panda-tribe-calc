package com.example.pandatribe.repositories;

import com.example.pandatribe.models.dtos.Blueprint;
import com.example.pandatribe.models.dtos.SystemName;
import com.example.pandatribe.models.industry.blueprints.BlueprintActivity;
import com.example.pandatribe.models.universe.SystemInfo;
import com.example.pandatribe.repositories.interfaces.EveCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EveCustomRepositoryImpl implements EveCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public BlueprintActivity getBluePrintInfoByProduct(Integer productId) {
        String nativeQuery = "SELECT quantity,\"typeID\",\"activityID\" FROM evesde.\"industryActivityProducts\" iap WHERE iap.\"productTypeID\" =:productId ";
        List<Object[]> result = entityManager.createNativeQuery(nativeQuery).setParameter("productId", productId).getResultList();
        return result.isEmpty() ? null :
                BlueprintActivity.builder()
                        .blueprintId((Integer) result.get(0)[1])
                        .craftQuantity((Integer) result.get(0)[0])
                        .activityId((Integer) result.get(0)[2])
                        .build();
    }

    public BlueprintActivity getBluePrintInfoByBlueprint(Integer blueprintId) {
        String nativeQuery = "SELECT quantity,\"typeID\",\"activityID\" FROM evesde.\"industryActivityProducts\" iap WHERE iap.\"typeID\" =:blueprintId ";
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

    public List<SystemName> getSystems(){
        String nativeQuery = "SELECT \"solarSystemName\" FROM evesde.\"mapSolarSystems\"";
        List<Object> result = entityManager.createNativeQuery(nativeQuery).getResultList();
        return result.stream().map(name-> SystemName.builder().systemName((String) name).build()).collect(Collectors.toList());
    }
    public Integer getVolume(Integer typeId){
        String nativeQuery = "SELECT \"volume\" FROM evesde.\"invVolumes\" iv WHERE iv.\"typeID\" = :typeId";
        List<Object> result = entityManager.createNativeQuery(nativeQuery).setParameter("typeId",typeId).getResultList();
        return result.isEmpty() ? null : (Integer) result.get(0);
    }

    public List<Blueprint> getBlueprints(){
        String nativeQuery = "SELECT \"productTypeID\" FROM evesde.\"industryActivityProducts\" iap WHERE iap.\"activityID\" = 1 OR iap.\"activityID\" = 11";
        List<Object> result = entityManager.createNativeQuery(nativeQuery).getResultList();
        return result.stream().map(id-> Blueprint.builder().blueprint(getBlueprintName((Integer) id)).build()).collect(Collectors.toList());
    }
    private String getBlueprintName(Integer id){
        String nativeQuery = "SELECT \"typeName\" FROM evesde.\"invTypes\" it WHERE it.\"typeID\" = :id";
        List<Object> result = entityManager.createNativeQuery(nativeQuery).setParameter("id",id).getResultList();
        return result.isEmpty() ? null : (String) result.get(0);
    }
}
