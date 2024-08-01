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

    @Override
    public ProductMarketData getProductData(String exchangeSlug, String product) throws MarketDataException {
        var productJsonString = redisService.getValue(String.format("%s:%s" ,exchangeSlug, product));

        ProductMarketData productMarketDataDto;

        try {
            productMarketDataDto = new ObjectMapper().readValue(productJsonString, ProductMarketData.class);
        } catch (Exception e) {
            throw new MarketDataException("Error deserializing product data from cache::", e);
        }

        return productMarketDataDto;
    }

    @Override
    public Map<String, ProductMarketData> getProductDataFromAllExchanges(String product) {
        var exchangesSlugs = exchangeRepository.findAllSlugs();
        Map<String, ProductMarketData> productsData = new HashMap<>();

        exchangesSlugs.forEach(s -> {
            var productJsonString = redisService.getValue(s + product);

            try {
                var productData = new ObjectMapper().readValue(productJsonString, ProductMarketData.class);

                productsData.put(s, productData);
            } catch (JsonProcessingException e) {
                log.warn("Error deserializing product data from cache", e);
            }
        });

        return productsData;
    }

    @Override
    public List<OpenOrderDto> getOpenOrdersFromOrderBook() {
        return List.of();
    }
}
