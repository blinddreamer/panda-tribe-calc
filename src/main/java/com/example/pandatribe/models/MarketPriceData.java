package com.example.pandatribe.models;

import lombok.Data;

import java.util.List;

@Data
public class MarketPriceData {
    List<ItemPrice> itemPriceList;
}
