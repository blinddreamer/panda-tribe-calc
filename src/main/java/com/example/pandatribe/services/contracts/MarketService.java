package com.example.pandatribe.services.contracts;

import com.example.pandatribe.models.market.ItemPrice;
import com.example.pandatribe.models.market.MarketPriceData;

import java.math.BigDecimal;
import java.util.List;

public interface MarketService {
    List<ItemPrice> getItemMarketPrice(Integer typeId);

    BigDecimal getItemPrice(Integer locationId, List<ItemPrice> itemPriceList);

    List<MarketPriceData> getMarketPriceData();
}
