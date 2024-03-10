package com.example.pandatribe.services;

import com.example.pandatribe.models.EveType;
import com.example.pandatribe.models.ItemPrice;
import com.example.pandatribe.models.dtos.MaterialDto;
import com.example.pandatribe.models.Material;
import com.example.pandatribe.repositories.interfaces.EveCustomRepository;
import com.example.pandatribe.repositories.interfaces.EveMaterialsRepository;
import com.example.pandatribe.repositories.interfaces.EveTypesRepository;
import com.example.pandatribe.services.contracts.MarketService;
import com.example.pandatribe.services.contracts.MaterialService;
import com.example.pandatribe.utils.Helper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MaterialsServiceImpl implements MaterialService {
    public static final Integer LOCATION_ID = 60003760;
    private final EveTypesRepository eveTypesRepository;
    private final EveMaterialsRepository materialBlueprintRepository;
    private final EveCustomRepository eveCustomRepository;
    private final MarketService marketService;
    private final Helper helper;

    @Override
    public List<MaterialDto> getMaterialsByActivity(Integer blueprintId, Integer quantity, Double discountBR, Integer discountBP, Integer discountB) {
        List<Material> materials = materialBlueprintRepository.findMaterialsByActivity(blueprintId);
        return getSimpleMaterials(materials, quantity, discountBR, discountBP, discountB);
    }

    private List<MaterialDto> getSimpleMaterials(List<Material> materials, Integer quantity, Double discountBR, Integer discountBP, Integer discountB){
        List<MaterialDto> materialDtoList = new ArrayList<>();

        for (Material material : materials) {
            List<ItemPrice> marketPriceData = marketService.getMarketPrices(material.getBlueprintTypeId().getMaterialTypeId());
            Optional<EveType> eveType = eveTypesRepository.findEveTypeByTypeId(material.getBlueprintTypeId().getMaterialTypeId());
            if (eveType.isEmpty()){
                continue;
            }
            Integer matBlueprintId=0;
            Double craftQuantity=1.0;
           List<Object[]> blueprints = eveCustomRepository.getBluePrintCraftQuantity(eveType.get().getTypeId());
            for (Object[] row : blueprints) {
                matBlueprintId = (Integer) row[1];
                craftQuantity = (Double.parseDouble(row[0].toString()));
            }
            Integer matQuantity = getQuantityDiscount(BigDecimal.valueOf(material.getQuantity() * quantity),discountBR, discountBP, discountB);
            Integer jobsCount = !blueprints.isEmpty() ? (int)Math.ceil(matQuantity/craftQuantity) : null;
           MaterialDto.MaterialDtoBuilder materialDto = MaterialDto.builder()
                    .name(eveType.get().getTypeName())
                    .neededQuantity(matQuantity)
                    .icon(helper.generateIconLink(eveType.get().getTypeId()))
                    .sellPrice(marketService.getItemPrice(LOCATION_ID,marketPriceData).multiply(BigDecimal.valueOf(matQuantity)))
                    .volume(eveType.get().getVolume()*matQuantity)
                    .jobsCount(jobsCount);

            Optional<EveType> materialBlueprint = eveTypesRepository.findEveTypeByTypeId(matBlueprintId);
            if (materialBlueprint.isEmpty() || materialBlueprint.get().getTypeId()==0){
                materialDto.subMaterials(Boolean.FALSE);
                materialDtoList.add(materialDto.build());
                continue;
            }
            materialDto.subMaterials(Boolean.TRUE);
            materialDtoList.add(materialDto.build());
        }
        return materialDtoList;
    }

    private Integer getQuantityDiscount(BigDecimal initialQuantity, Double discountBR, Integer discountBP, Integer discountB) {
        initialQuantity =  initialQuantity.subtract(initialQuantity.multiply(BigDecimal.valueOf(discountBP/100.0)));
        initialQuantity = initialQuantity.subtract(initialQuantity.multiply(BigDecimal.valueOf(discountBR/100.0)));
        initialQuantity = initialQuantity.subtract(initialQuantity.multiply(BigDecimal.valueOf(discountB/100.0)));
        return initialQuantity.setScale(0, BigDecimal.ROUND_CEILING).intValue();
    }

}
