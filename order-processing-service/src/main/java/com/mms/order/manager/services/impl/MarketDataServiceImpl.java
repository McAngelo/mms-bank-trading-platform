package com.mms.order.manager.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mms.order.manager.dtos.internal.ProductData;
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
    public ProductData getProductData(String exchangeSlug, String product) throws MarketDataException {
        var productJsonString = redisService.getValue(exchangeSlug + product);
        ProductData productDataDto;

        try {
            productDataDto = new ObjectMapper().readValue(productJsonString, ProductData.class);
        } catch (JsonProcessingException e) {
            throw new MarketDataException("Error deserializing product data from cache");
        }

        return productDataDto;
    }

    @Override
    public Map<String, ProductData> getProductDataFromAllExchanges(String product) {
        var exchangesSlugs = exchangeRepository.findAllSlugs();
        Map<String, ProductData> productsData = new HashMap<>();

        exchangesSlugs.forEach(s -> {
            var productJsonString = redisService.getValue(s + product);

            try {
                var productData = new ObjectMapper().readValue(productJsonString, ProductData.class);

                productsData.put(s, productData);
            } catch (JsonProcessingException e) {
                log.warn("Error deserializing product data from cache", e);
            }
        });

        return productsData;
    }
}
