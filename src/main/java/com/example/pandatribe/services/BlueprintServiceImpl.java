package com.example.pandatribe.services;

import com.example.pandatribe.models.requests.BlueprintRequest;
import com.example.pandatribe.models.results.*;
import com.example.pandatribe.models.industry.CostIndex;
import com.example.pandatribe.models.industry.blueprints.BlueprintActivity;
import com.example.pandatribe.models.industry.blueprints.EveType;
import com.example.pandatribe.models.universe.Region;
import com.example.pandatribe.models.universe.Station;
import com.example.pandatribe.models.universe.SystemInfo;
import com.example.pandatribe.repositories.interfaces.EveCustomRepository;
import com.example.pandatribe.repositories.interfaces.EveTypesRepository;
import com.example.pandatribe.services.contracts.BlueprintService;
import com.example.pandatribe.services.contracts.IndustryService;
import com.example.pandatribe.services.contracts.MarketService;
import com.example.pandatribe.services.contracts.MaterialService;
import com.example.pandatribe.utils.Helper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BlueprintServiceImpl implements BlueprintService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlueprintServiceImpl.class);
    public static final Integer REACTION_ACTIVITY_ID = 11;
    public static final Integer REGION_ID = 10000002;
    public static final String DEFAULT_SYSTEM = "Jita";
    public static final Integer DEFAULT_LOCATION_ID = 60003760;
    public static final String REACTION = "reaction";
    public static final String MANUFACTURING = "manufacturing";
    private final MaterialService materialsService;
    private final MarketService marketService;
    private final EveTypesRepository repository;
    private final EveCustomRepository eveCustomRepository;
    private final IndustryService industryService;
    private final Helper helper;


    @Override
    @Transactional
    public BlueprintResult getBlueprintData(BlueprintRequest blueprintRequest){
        Boolean init = Optional.ofNullable(blueprintRequest.getInit()).orElse(false);
        Integer quantity = Optional.ofNullable(blueprintRequest.getQuantity()).orElse(1);
        Integer blueprintMaterialEfficiency = Optional.ofNullable(blueprintRequest.getBlueprintMe()).orElse(0);
        Integer rigDiscount = Optional.ofNullable(blueprintRequest.getBuildingRig()).orElse(0);
        Integer buildingDiscount = Optional.ofNullable(blueprintRequest.getBuilding()).orElse(0);
        String system = Optional.ofNullable(blueprintRequest.getSystem()).filter(s -> !s.isEmpty()).orElse(DEFAULT_SYSTEM);
        Double facilityTax = Optional.ofNullable(blueprintRequest.getFacilityTax()).orElse(0.0);
        String blueprintName = blueprintRequest.getBlueprintName();
        Integer jobRuns = Optional.ofNullable(blueprintRequest.getJobRuns()).orElse(1);
        Integer regionId = Optional.ofNullable(blueprintRequest.getRegionId()).orElse(REGION_ID);

        Optional<EveType> eveType = repository.findEveTypeByTypeName(blueprintName);
        if (eveType.isEmpty()){
            return null;
        }
        Integer size = init ? 256 : 32;
        BlueprintActivity blueprintActivity = eveCustomRepository.getBluePrintInfoByProduct(eveType.get().getTypeId());
        if(Objects.nonNull(blueprintActivity)) {
            SystemInfo systemInfo = eveCustomRepository.getSystemInfo(system);
        if(Objects.isNull(systemInfo)){
            systemInfo = eveCustomRepository.getSystemInfo(DEFAULT_SYSTEM);
        }
            Integer volume = eveCustomRepository.getVolume(eveType.get().getTypeId());
            Integer  matBlueprintId = blueprintActivity.getBlueprintId();
            List<BlueprintResult> materialsList = materialsService.getMaterialsByActivity(matBlueprintId, quantity, rigDiscount, blueprintMaterialEfficiency, buildingDiscount, systemInfo.getSecurity(), jobRuns, regionId);
            String activity = blueprintActivity.getActivityId().equals(REACTION_ACTIVITY_ID) ? REACTION : MANUFACTURING;
            BigDecimal industryCosts = calculateIndustryTaxes(facilityTax, systemInfo.getSystemId(), materialsList, activity, buildingDiscount);

            return BlueprintResult.builder()
                    .name(blueprintName)
                    .volume((Objects.nonNull(volume)? volume : eveType.get().getVolume()) * quantity *jobRuns)
                    .isCreatable(Boolean.TRUE)
                    .quantity(quantity* jobRuns)
                    .materialsList(materialsList)
                    .industryCosts(industryCosts)
                    .icon(eveType.get().getGroupId().equals(541) ? helper.generateRenderLink(eveType.get().getTypeId(),size) : helper.generateIconLink(eveType.get().getTypeId(),size))
                    .sellPrice(marketService
                            .getItemPrice(DEFAULT_LOCATION_ID, marketService.getItemMarketPrice(eveType.get().getTypeId(),regionId))
                            .multiply(BigDecimal.valueOf(quantity))
                            .multiply(BigDecimal.valueOf(jobRuns)))
                    .build();

        }
       return null;
    }

    @Override
    public GetBlueprintsResult getEveBlueprints() {
        List<Blueprint> blueprints = eveCustomRepository.getBlueprints().stream().filter(bp-> Objects.nonNull(bp.getBlueprint())).toList();
        LOGGER.info("Blueprints loaded - {}", !blueprints.isEmpty());
        return GetBlueprintsResult.builder()
                .blueprints(blueprints)
                .build();
    }

    @Override
    public List<SystemName> getEveSystems() {
        List<SystemName> systems =  eveCustomRepository.getSystems();
        LOGGER.info("Regions loaded - {}", !systems.isEmpty());
        return systems;
    }

    @Override
    public List<Region> getEveRegions() {
        List<Region> regions = eveCustomRepository.getRegions();
        LOGGER.info("Regions loaded - {}", !regions.isEmpty());
        return regions;
    }

    @Override
    public List<Station> getEveStations() {
        List<Station> stations = eveCustomRepository.getStations();
        LOGGER.info("Stations loaded - {}", !stations.isEmpty());
        return stations;
    }

    private BigDecimal calculateIndustryTaxes(Double facilityTax, Integer systemId, List<BlueprintResult> materials, String activity, Integer buildingIndex){
        BigDecimal eiv = materials.stream().map(BlueprintResult::getAdjustedPrice).reduce(BigDecimal.ZERO,BigDecimal::add);
        Integer buildingBonus = helper.getBuildingBonus(buildingIndex).getCostReduction();
        Double surcharge = 4.0;
        Double costIndex = industryService.getSystemCostIndexes().stream()
                .filter(c -> c.getSystemId().equals(systemId))
                .flatMap(systemIndex -> systemIndex.getCostIndexes().stream())
                .filter(c -> c.getActivity().equals(activity))
                .findFirst()
                .map(CostIndex::getCostIndex)
                .orElse(0.0);
        BigDecimal price = eiv.multiply(BigDecimal.valueOf(costIndex)).setScale(0, RoundingMode.CEILING);
        price = price.subtract(price.multiply(BigDecimal.valueOf(buildingBonus/100)));
        price = price.add(eiv.multiply(BigDecimal.valueOf(facilityTax/100)));
        price = price.add(eiv.multiply(BigDecimal.valueOf(surcharge/100)));
        return price.setScale(0, RoundingMode.CEILING);
    }
}
