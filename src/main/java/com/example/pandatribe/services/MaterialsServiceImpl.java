package com.example.pandatribe.services;

import com.example.pandatribe.models.industry.BuildingBonus;
import com.example.pandatribe.models.industry.RigBonus;
import com.example.pandatribe.models.industry.blueprints.BlueprintActivity;
import com.example.pandatribe.models.industry.blueprints.EveType;
import com.example.pandatribe.models.market.ItemPrice;
import com.example.pandatribe.models.dtos.MaterialDto;
import com.example.pandatribe.models.industry.blueprints.Material;
import com.example.pandatribe.models.market.MarketPriceData;
import com.example.pandatribe.repositories.interfaces.EveCustomRepository;
import com.example.pandatribe.repositories.interfaces.EveMaterialsRepository;
import com.example.pandatribe.repositories.interfaces.EveTypesRepository;
import com.example.pandatribe.services.contracts.MarketService;
import com.example.pandatribe.services.contracts.MaterialService;
import com.example.pandatribe.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MaterialsServiceImpl implements MaterialService {
    public static final Integer LOCATION_ID = 60003760;
    private final EveTypesRepository eveTypesRepository;
    private final EveMaterialsRepository materialBlueprintRepository;
    private final EveCustomRepository eveCustomRepository;
    private final MarketService marketService;
    private final Helper helper;
    @Value("${SKIP_REACTIONS}")
    private Boolean reactions;


    @Override
    public List<MaterialDto> getMaterialsByActivity(Integer blueprintId, Integer quantity, Integer discountBR, Integer materialEfficiency, Integer discountB, Double security) {
        List<Material> materials = materialBlueprintRepository.findMaterialsByActivity(blueprintId);
        return getSimpleMaterials(materials, quantity, discountBR, materialEfficiency, discountB, security);
    }

    private List<MaterialDto> getSimpleMaterials(List<Material> materials, Integer quantity, Integer discountBR, Integer materialEfficiency, Integer discountB, Double security) {
        List<MaterialDto> materialDtoList = new ArrayList<>();
        RigBonus rigBonus = helper.getRigBonus(discountBR);
        BuildingBonus buildingBonus = helper.getBuildingBonus(discountB);
        List<MarketPriceData> marketPriceData = marketService.getMarketPriceData();
        Double rigMultiplier = getRigMultiplier(rigBonus, security);
        for (Material material : materials) {
            List<ItemPrice> marketItemPriceData = marketService.getItemMarketPrice(material.getBlueprintTypeId().getMaterialTypeId());
            Optional<EveType> eveType = eveTypesRepository.findEveTypeByTypeId(material.getBlueprintTypeId().getMaterialTypeId());
            if (eveType.isEmpty()) {
                continue;
            }

            BlueprintActivity blueprintActivity = eveCustomRepository.getBluePrintInfo(eveType.get().getTypeId());
            Integer volume = eveCustomRepository.getVolume(eveType.get().getTypeId());
            Double craftQuantity = Optional.ofNullable(blueprintActivity).map(b -> Double.parseDouble(b.getCraftQuantity().toString())).orElse(0.0);
            Integer matQuantity = getQuantityDiscount(BigDecimal.valueOf(material.getQuantity() * quantity), rigBonus.getMaterialReduction() * rigMultiplier, materialEfficiency, buildingBonus.getMaterialReduction());
            Integer jobsCount = Objects.nonNull(blueprintActivity) ? (int) Math.ceil(matQuantity / craftQuantity) : null;
            MaterialDto.MaterialDtoBuilder materialDto = MaterialDto.builder()
                    .name(eveType.get().getTypeName())
                    .neededQuantity(matQuantity)
                    .icon(helper.generateIconLink(eveType.get().getTypeId()))
                    .sellPrice(marketService.getItemPrice(LOCATION_ID, marketItemPriceData).multiply(BigDecimal.valueOf(matQuantity)))
                    .volume(Objects.nonNull(volume) ? volume : eveType.get().getVolume() * matQuantity)
                    .adjustedPrice(marketPriceData.stream()
                            .filter(m-> m.getTypeId().equals(eveType.get().getTypeId()))
                            .findFirst()
                            .map(MarketPriceData::getAdjustedPrice)
                            .orElse(BigDecimal.ZERO).multiply(BigDecimal.valueOf(matQuantity)))
                    .jobsCount(jobsCount);

            if (Objects.isNull(blueprintActivity)) {
                materialDto.subMaterials(Boolean.FALSE);
                materialDtoList.add(materialDto.build());
                continue;
            }
            Boolean skip = reactions ? blueprintActivity.getActivityId() == 11 ? true : false : false;
            materialDto.subMaterials(skip ? Boolean.FALSE : Boolean.TRUE);
            materialDtoList.add(materialDto.build());
        }
        return materialDtoList;
    }

    private Integer getQuantityDiscount(BigDecimal initialQuantity, Double discountBR, Integer discountBP, Integer discountB) {
        initialQuantity = initialQuantity.subtract(initialQuantity.multiply(BigDecimal.valueOf(discountBP / 100.0)));
        initialQuantity = initialQuantity.subtract(initialQuantity.multiply(BigDecimal.valueOf(discountBR / 100.0)));
        initialQuantity = initialQuantity.subtract(initialQuantity.multiply(BigDecimal.valueOf(discountB / 100.0)));
        return initialQuantity.setScale(0, RoundingMode.CEILING).intValue();
    }

    private Double getRigMultiplier(RigBonus rigBonus, Double security) {
        if (security >= 0.5) {
            return rigBonus.getHighSecMultiplier();
        }
        if (security < 0.5 && security > 0) {
            return rigBonus.getLowSecMultiplier();
        }
        return rigBonus.getNullSecMultiplier();
    }
}
