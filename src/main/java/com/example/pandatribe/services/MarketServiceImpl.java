package com.example.pandatribe.services;

import com.example.pandatribe.feign.contracts.EveInteractor;
import com.example.pandatribe.models.market.ItemPrice;
import com.example.pandatribe.models.market.MarketPriceData;
import com.example.pandatribe.services.contracts.MarketService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
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


    @Override
    public List<ItemPrice> getItemMarketPrice(Integer typeId, Integer regionId) {

        return eveInteractor.getItemMarketPrice(regionId,DATA_SOURCE,ORDER_TYPE,typeId) ;
    }

    @Override
    public BigDecimal getItemPrice(Integer locationId, List<ItemPrice> itemPriceList) {
        return itemPriceList.stream()
             //   .filter(itemPrice -> Objects.equals(itemPrice.getLocationId(), locationId))
                .map(ItemPrice::getPrice)
                .sorted()
                .findFirst().orElse(BigDecimal.ZERO);

    }

    @Override
    @Cacheable("cacheMarketPrices")
    public List<MarketPriceData> getMarketPriceData(){
        return eveInteractor.getMarketPrices();
    }
}
