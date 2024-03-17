package com.example.pandatribe.feign.contracts;

import com.example.pandatribe.models.industry.SystemCostIndexes;
import com.example.pandatribe.models.market.ItemPrice;
import com.example.pandatribe.models.market.MarketPriceData;
import com.example.pandatribe.models.authentication.TokenResponse;
import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

import java.util.List;
import java.util.Map;


@Headers({"Accept: application/json",
"Content-type: application/json"})
public interface EveApiList {

    @RequestLine("POST /v2/oauth/token")
    TokenResponse requestAccessToken(@Param("grant_type")String grantType,@Param("client_code") String clientCode,@Param("client_secret") String clientSecret);

    @RequestLine("GET /markets/{regionId}/orders/?datasource={dataSource}&order_type={orderType}&type_id={typeId}")
    List<ItemPrice> getMarketData(@Param("regionId") Integer regionId, @QueryMap Map<String,Object> queryParams);

    @RequestLine("GET /markets/prices")
    List<MarketPriceData> getMarketPrices();

    @RequestLine("GET /industry/systems")
    List<SystemCostIndexes> getSystemCostIndexes();
}
