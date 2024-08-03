package com.mms.market_data_service.tasks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mms.market_data_service.dtos.responses.ProductData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mms.market_data_service.services.interfaces.ExchangeService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledTasks {
    private final ExchangeService exchangeService;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    private static final Duration CACHE_EXPIRY_DURATION = Duration.ofHours(1);

    @PostConstruct
    public void onStartup() {
        populateCacheForExchange("EXCHANGE1");
        populateCacheForExchange("EXCHANGE2");
        log.info("Populated cache on startup");
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void updateProductDataCache() {
        List.of("EXCHANGE1", "EXCHANGE2").forEach(this::populateCacheForExchange);
    }

    private void populateCacheForExchange(String exchange) {
        try {
            List<ProductData> productsData = exchangeService.getProductDataFromExchange(exchange);
            cacheProductData(exchange, productsData);
            log.info("Updated product data for {} in cache", exchange);
        } catch (Exception e) {
            log.error("Failed to update product data for {} in cache: {}", exchange, e.getMessage(), e);
        }
    }

    private void cacheProductData(String exchange, List<ProductData> products) {
        var key = String.format("products:%s", exchange);

        try {
            redisTemplate.delete(key);
            for (ProductData product : products) {
                String productJson = objectMapper.writeValueAsString(product);
                redisTemplate.opsForList().rightPush(key, productJson);
            }
            redisTemplate.expire(key, CACHE_EXPIRY_DURATION);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize product data for {}: {}", exchange, e.getMessage(), e);
        } catch (Exception e) {
            log.error("Unexpected error while caching product data for {}: {}", exchange, e.getMessage(), e);
        }
    }
}
