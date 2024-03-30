package com.example.pandatribe.repositories;

import com.example.pandatribe.models.market.Category;
import com.example.pandatribe.models.results.Blueprint;
import com.example.pandatribe.models.results.SystemName;
import com.example.pandatribe.models.industry.blueprints.BlueprintActivity;
import com.example.pandatribe.models.universe.Region;
import com.example.pandatribe.models.universe.Station;
import com.example.pandatribe.models.universe.SystemInfo;
import com.example.pandatribe.repositories.interfaces.EveCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EveCustomRepositoryImpl implements EveCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

@Transactional
    @Override
    public BlueprintActivity getBluePrintInfoByProduct(Integer productId) {
        String nativeQuery = "SELECT quantity,\"typeID\",\"activityID\" FROM public.\"industryActivityProducts\" iap WHERE iap.\"productTypeID\" =:productId ";
        List<Object[]> result = entityManager.createNativeQuery(nativeQuery).setParameter("productId", productId).getResultList();
        return result.isEmpty() ? null :
                BlueprintActivity.builder()
                        .blueprintId((Integer) result.get(0)[1])
                        .craftQuantity((Integer) result.get(0)[0])
                        .activityId((Integer) result.get(0)[2])
                        .build();
    }

    @Transactional
    public BlueprintActivity getBluePrintInfoByBlueprint(Integer blueprintId) {
        String nativeQuery = "SELECT quantity,\"typeID\",\"activityID\" FROM public.\"industryActivityProducts\" iap WHERE iap.\"typeID\" =:blueprintId ";
        List<Object[]> result = entityManager.createNativeQuery(nativeQuery).setParameter("blueprintId", blueprintId).getResultList();
        return result.isEmpty() ? null :
                BlueprintActivity.builder()
                        .blueprintId((Integer) result.get(0)[1])
                        .craftQuantity((Integer) result.get(0)[0])
                        .activityId((Integer) result.get(0)[2])
                        .build();
    }

    @Transactional
    public SystemInfo getSystemInfo(String systemName){
        String nativeQuery = "SELECT \"solarSystemID\", \"security\" FROM public.\"mapSolarSystems\" mss WHERE mss.\"solarSystemName\" = :systemName";
        List<Object[]> result = entityManager.createNativeQuery(nativeQuery).setParameter("systemName",systemName).getResultList();
        return result.isEmpty() ? null : SystemInfo.builder().systemId((Integer) result.get(0)[0]).security((Double) result.get(0)[1]).build();
    }

    @Transactional
    public List<SystemName> getSystems(){
        String nativeQuery = "SELECT \"solarSystemName\" FROM public.\"mapSolarSystems\"";
        List<Object> result = entityManager.createNativeQuery(nativeQuery).getResultList();
        return result.stream().map(name-> SystemName.builder().systemName((String) name).build()).collect(Collectors.toList());
    }
    @Transactional
    public Integer getVolume(Integer typeId){
        String nativeQuery = "SELECT \"volume\" FROM public.\"invVolumes\" iv WHERE iv.\"typeID\" = :typeId";
        List<Object> result = entityManager.createNativeQuery(nativeQuery).setParameter("typeId",typeId).getResultList();
        return result.isEmpty() ? null : (Integer) result.get(0);
    }

    @Transactional
    public List<Blueprint> getBlueprints(){
        String nativeQuery = "SELECT \"productTypeID\" FROM public.\"industryActivityProducts\" iap WHERE iap.\"activityID\" = 1 OR iap.\"activityID\" = 11";
        List<Object> result = entityManager.createNativeQuery(nativeQuery).getResultList();
        return result.stream().map(id-> Blueprint.builder().blueprint(getBlueprintName((Integer) id)).build()).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<Region> getRegions() {
        String nativeQuery = "SELECT \"regionID\", \"regionName\" FROM public.\"mapRegions\" ORDER BY \"regionName\" ASC";
        List<Object[]> result = entityManager.createNativeQuery(nativeQuery).getResultList();
        return result.stream().map(region -> Region.builder().regionId((Integer) region[0]).regionName((String) region[1]).build())
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<Station> getStations() {
        String nativeQuery = "SELECT \"stationID\", \"stationName\" FROM public.\"staStations\" ORDER BY \"stationName\" ASC";
        List<Object[]> result = entityManager.createNativeQuery(nativeQuery).getResultList();
        return result.stream().map(station -> Station.builder().stationId((Long) station[0]).stationName((String) station[1]).build())
                .collect(Collectors.toList());
    }

    @Override
    public List<Category> getCategories() {
        return null;
    }


    private String getBlueprintName(Integer id){
        String nativeQuery = "SELECT \"typeName\" FROM public.\"invTypes\" it WHERE it.\"typeID\" = :id";
        List<Object> result = entityManager.createNativeQuery(nativeQuery).setParameter("id",id).getResultList();
        return result.isEmpty() ? null : (String) result.get(0);
    }
}
