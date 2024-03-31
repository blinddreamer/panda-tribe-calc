package com.example.pandatribe.services;

import com.example.pandatribe.feign.contracts.EveInteractor;
import com.example.pandatribe.models.industry.blueprints.EveType;
import com.example.pandatribe.models.market.ItemPrice;
import com.example.pandatribe.models.requests.AppraisalRequest;
import com.example.pandatribe.models.results.AppraisalResult;
import com.example.pandatribe.models.results.AppraisalResultEntity;
import com.example.pandatribe.repositories.interfaces.EveTypesRepository;
import com.example.pandatribe.services.contracts.AppraisalService;
import com.example.pandatribe.services.contracts.MarketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppraisalServiceImpl implements AppraisalService {
    private final MarketService marketService;
    private final EveTypesRepository eveTypesRepository;

    @Override
    public AppraisalResult generateAppraisalResult(AppraisalRequest appraisalRequest) {
     List<AppraisalResultEntity> appraisalEntities = appraisalRequest.getAppraisalRequestEntityList().stream().map(appraisal ->{
            EveType eveType = eveTypesRepository.findEveTypeByTypeName(appraisal.getName()).orElse(null);
            if(Objects.isNull(eveType)){
                return null;
            }
            List<ItemPrice> itemPriceList = marketService
                    .getItemMarketPrice(eveType.getTypeId(),appraisalRequest.getRegionId());

            return AppraisalResultEntity.builder()
                    .quantity(appraisal.getQuantity())
                    .volume(eveType.getVolume())
                    .item(eveType.getTypeName())
                    .buyOrderAverage()
                    .buyOrdersCount()
                    .buyOrderPrice(marketService.getItemPrice(null, itemPriceList))
                    .sellOrderPrice()
                    .sellOrderAverage()
                    .sellOrdersCount()
                    .build();
                }).collect(Collectors.toList());


        return AppraisalResult.builder()
                .appraisals(appraisalEntities)
                .build();
    }
}
