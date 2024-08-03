package com.mms.order.manager.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mms.order.manager.dtos.internal.OpenOrderDto;
import com.mms.order.manager.dtos.internal.ProductMarketData;
import com.mms.order.manager.exceptions.MarketDataException;
import com.mms.order.manager.repositories.ExchangeRepository;
import com.mms.order.manager.services.interfaces.MarketDataService;
import com.mms.order.manager.services.interfaces.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MarketDataServiceImpl implements MarketDataService {
    private final RedisService redisService;
    private final ExchangeRepository exchangeRepository;
    private final ObjectMapper objectMapper;

    @Override
    public ProductMarketData getProductData(String exchangeSlug, String productTicker) throws MarketDataException {
        String key = String.format("products:%s", exchangeSlug);

        List<String> productJsonList = redisService.getListValues(key);

        for (String productJson : productJsonList) {
            try {
                ProductMarketData productData = objectMapper.readValue(productJson, ProductMarketData.class);
                if (productData.ticker().equalsIgnoreCase(productTicker)) {
                    return productData;
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        throw new MarketDataException("Product not found in cache for exchange: " + exchangeSlug);
    }

    @Override
    public Map<String, ProductMarketData> getProductDataFromAllExchanges(String productTicker) {
        List<String> exchangesSlugs = exchangeRepository.findAllSlugs();
        Map<String, ProductMarketData> productsData = new HashMap<>();

        exchangesSlugs.forEach(s -> {
            try {
                ProductMarketData productData = getProductData(s, productTicker);
                productsData.put(s, productData);
            } catch (MarketDataException e) {
                log.warn("Error retrieving product data for exchange {}: {}", s, e.getMessage());
            }
        });

        return productsData;
    }

    @Override
    public List<OpenOrderDto> getOpenOrdersFromOrderBook() {
        return List.of();
    }
}
