package com.example.pandatribe.feign;

import com.example.pandatribe.feign.contracts.EveApiList;
import com.example.pandatribe.feign.contracts.EveInteractor;
import com.example.pandatribe.models.ItemPrice;
import com.example.pandatribe.models.MarketPriceData;
import com.example.pandatribe.models.TokenRequest;
import com.example.pandatribe.models.TokenResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class EveInteractorImpl implements EveInteractor {
    private final FeignConfig feign;
    public static final String API_ADDRESS = "https://esi.evetech.net/latest";
    public static final String AUTH_ADDRESS = "https://login.eveonline.com";
    @Override
    public TokenResponse requestAccessToken(TokenRequest request) {
        return feign.getRestClient(EveApiList.class,AUTH_ADDRESS)
                .requestAccessToken(request.getGrant_type(), request.getClient_id(), request.getClient_secret());
    }

    @Override
    public List<ItemPrice> getMarketPrices(Integer regionId, String dataSource, String orderType, Integer typeId) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("datasource",dataSource);
        queryParams.put("order_type",orderType);
        queryParams.put("type_id",typeId);
        return feign.getRestClient(EveApiList.class,API_ADDRESS).getMarketData(regionId, queryParams);
    }
}
