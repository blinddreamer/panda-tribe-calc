package com.example.pandatribe.feign.contracts;

import com.example.pandatribe.models.ItemPrice;
import com.example.pandatribe.models.MarketPriceData;
import com.example.pandatribe.models.TokenRequest;
import com.example.pandatribe.models.TokenResponse;

import java.util.List;

public interface EveInteractor {
    TokenResponse requestAccessToken(TokenRequest request);
    List<ItemPrice> getMarketPrices(Integer regionId, String dataSource, String orderType, Integer typeId);
}
