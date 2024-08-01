package com.mms.market_data_service.tasks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mms.market_data_service.helper.JsonHelper;
import com.mms.market_data_service.services.interfaces.ExchangeService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledTasks {
    private final ExchangeService exchangeService;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void onStartup() {
        getProductsDataFromExchange1();
        getProductsDataFromExchange2();
        log.info("Populated cache on start up");
    }

    @Scheduled(cron = "0 * * * * *")
    public void getProductsDataFromExchange1() {
        try {
            var EXCHANGE = "EXCHANGE1";
            var productsData = exchangeService.getProductDataFromExchange(EXCHANGE);

            productsData.forEach(p -> {
                var key = String.format("%s:%s", EXCHANGE, p.ticker());

                try {
                    var productJson = objectMapper.writeValueAsString(p);
                    redisTemplate.opsForValue().set(key, productJson);

                    LocalDateTime now = LocalDateTime.now();
                    LocalDateTime expiryDateTime = now.plusHours(1);

                    redisTemplate.expire(key, Duration.between(now, expiryDateTime));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });

            log.info("Invalidated exchange1 products data in cache");
        } catch (Exception e) {
            log.error("Could not invalidated exchange1 products data in cache {}", e.toString());
        }
    }

    @Scheduled(cron = "0 * * * * *")
    public void getProductsDataFromExchange2() {
        try {
            var EXCHANGE = "EXCHANGE2";
            var productsData = exchangeService.getProductDataFromExchange(EXCHANGE);

            productsData.forEach(p -> {
                var key = String.format("%s:%s", EXCHANGE, p.ticker());

                try {
                    redisTemplate.opsForValue().set(key, JsonHelper.toJson(p));
                } catch (JsonProcessingException e) {
                    log.error("Could not save product data {} to cache {}", p, e.toString());
                    return;
                }

                LocalDateTime now = LocalDateTime.now();
                LocalDateTime expiryDateTime = now.plusHours(1);

                redisTemplate.expire(key, Duration.between(now, expiryDateTime));
            });

            log.info("Invalidated exchange2 products data in cache");
        } catch (Exception e) {
            log.error("Could not invalidated exchange2 products data in cache {}", e.toString());
        }
    }
}
