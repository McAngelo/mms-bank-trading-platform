package com.mms.order.manager.services.interfaces;

import com.mms.order.manager.dtos.internal.ProductData;
import com.mms.order.manager.exceptions.MarketDataException;

import java.util.Map;



public interface MarketDataService {
    ProductData getProductData(String exchangeSlug, String productId) throws MarketDataException;

    Map<String, ProductData> getProductDataFromAllExchanges(String product);
}
