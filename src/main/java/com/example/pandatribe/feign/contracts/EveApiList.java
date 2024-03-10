package com.example.pandatribe.feign.contracts;

import com.example.pandatribe.models.ItemPrice;
import com.example.pandatribe.models.MarketPriceData;
import com.example.pandatribe.models.TokenResponse;
import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


@Headers({"Accept: application/json",
"Content-type: application/json"})
public interface EveApiList {

    @RequestLine("POST /v2/oauth/token")
    TokenResponse requestAccessToken(@Param("grant_type")String grantType,@Param("client_code") String clientCode,@Param("client_secret") String clientSecret);

    @RequestLine("GET /markets/{regionId}/orders/?datasource={dataSource}&order_type={orderType}&type_id={typeId}")
    List<ItemPrice> getMarketData(@Param("regionId") Integer regionId, @QueryMap Map<String,Object> queryParams);
}
