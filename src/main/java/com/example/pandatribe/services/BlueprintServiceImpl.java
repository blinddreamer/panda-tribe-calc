package com.example.pandatribe.services;

import com.example.pandatribe.models.*;
import com.example.pandatribe.models.dtos.BlueprintDto;
import com.example.pandatribe.models.dtos.MaterialDto;
import com.example.pandatribe.models.dtos.SearchDto;
import com.example.pandatribe.repositories.interfaces.EveCustomRepository;
import com.example.pandatribe.repositories.interfaces.EveTypesRepository;
import com.example.pandatribe.services.contracts.BlueprintService;
import com.example.pandatribe.services.contracts.MaterialService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BlueprintServiceImpl implements BlueprintService {
    private final MaterialService materialsService;
    private final EveTypesRepository repository;
    private final EveCustomRepository eveCustomRepository;

    @Override
    public BlueprintDto generateConstructionData(SearchDto searchDto){
        Integer quantity = Optional.ofNullable(searchDto.getQuantity()).orElse(1);
        Integer discountBP = Optional.ofNullable(searchDto.getBlueprintMe()).orElse(0);
        Double discountBR = Optional.ofNullable(searchDto.getBuildingRig()).orElse(0.0);
        Integer discountB = Optional.ofNullable(searchDto.getBuilding()).orElse(0);
        String blueprintName = searchDto.getBlueprintName();
        Optional<EveType> eveType = repository.findEveTypeByTypeName(blueprintName);
        if (eveType.isEmpty()){
            return null;
        }
        List<Object[]> blueprints = eveCustomRepository.getBluePrintCraftQuantity(eveType.get().getTypeId());
        for (Object[] row : blueprints) {
          Integer  matBlueprintId = (Integer) row[1];
            List<MaterialDto> materialsList = materialsService.getMaterialsByActivity(matBlueprintId, quantity, discountBR, discountBP, discountB);
            return BlueprintDto.builder()
                    .blueprintName(blueprintName)
                    .materialsList(materialsList)
                    .build();
        }

       return null;
    }
//    private String handleBlueprintName(String blueprintName){
//        StringBuilder result = new StringBuilder();
//        String[] bla = blueprintName.split(" ");
//        for (String part : bla) {
//           result.append(part.substring(0, 1).toUpperCase()).append(part.substring(1));
//           result.append(" ");
//        }
//        return result.toString().trim();
//    }
}
