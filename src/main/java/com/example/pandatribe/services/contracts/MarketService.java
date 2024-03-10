package com.example.pandatribe.services.contracts;

import com.example.pandatribe.models.ItemPrice;

import java.math.BigDecimal;
import java.util.List;

public interface MarketService {
    List<ItemPrice> getMarketPrices(Integer typeId);

    BigDecimal getItemPrice(Integer locationId, List<ItemPrice> itemPriceList);
}
