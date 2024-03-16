package com.example.pandatribe.feign.contracts;

import com.example.pandatribe.models.industry.SystemCostIndexes;
import com.example.pandatribe.models.market.ItemPrice;
import com.example.pandatribe.models.market.MarketPriceData;
import com.example.pandatribe.models.authentication.TokenRequest;
import com.example.pandatribe.models.authentication.TokenResponse;

import java.util.List;

public interface EveInteractor {
    TokenResponse requestAccessToken(TokenRequest request);
    List<ItemPrice> getItemMarketPrice(Integer regionId, String dataSource, String orderType, Integer typeId);

    List<MarketPriceData> getMarketPrices();

    List<SystemCostIndexes> getSystemCostIndexes();
}
