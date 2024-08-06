package com.mms.order.manager.services.interfaces;

import com.mms.order.manager.dtos.internal.OpenOrderDto;
import com.mms.order.manager.dtos.internal.ProductMarketData;
import com.mms.order.manager.exceptions.MarketDataException;

import java.util.List;
import java.util.Map;



public interface MarketDataService {
    ProductMarketData getProductData(String exchangeSlug, String productId) throws MarketDataException;

    Map<String, ProductMarketData> getProductDataFromAllExchanges(String product);

    List<OpenOrderDto>  getOpenOrdersFromOrderBook(String exchangeSlug, String productTicker);
}

