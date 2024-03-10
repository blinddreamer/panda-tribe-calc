package com.example.pandatribe.services;

import com.example.pandatribe.feign.contracts.EveInteractor;
import com.example.pandatribe.models.ItemPrice;
import com.example.pandatribe.models.MarketPriceData;
import com.example.pandatribe.services.contracts.MarketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class MarketServiceImpl implements MarketService {

    private final EveInteractor eveInteractor;
    public static final String DATA_SOURCE = "tranquility";
    public static final String ORDER_TYPE = "sell";
    public static final Integer REGION_ID = 10000002;

    @Override
    public List<ItemPrice> getMarketPrices(Integer typeId) {

        return eveInteractor.getMarketPrices(REGION_ID,DATA_SOURCE,ORDER_TYPE,typeId) ;
    }

    @Override
    public BigDecimal getItemPrice(Integer systemId, List<ItemPrice> itemPriceList) {
        return itemPriceList.stream()
                .filter(itemPrice -> Objects.equals(itemPrice.getLocationId(), systemId))
                .map(ItemPrice::getPrice)
                .findFirst().orElse(BigDecimal.ZERO);

    }
}
