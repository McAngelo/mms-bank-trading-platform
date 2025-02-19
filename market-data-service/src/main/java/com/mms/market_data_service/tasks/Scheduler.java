package com.mms.market_data_service.tasks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mms.market_data_service.config.AppProperties;
import com.mms.market_data_service.dtos.responses.ProductData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
@EnableAsync
public class Scheduler {

    private final SimpMessagingTemplate messagingTemplate;
    private final RedisTemplate<String, String> redisTemplate;
    private final AppProperties appProperties;
    private static final String exchange1 = "EXCHANGE1";
    private static final String exchange2 = "EXCHANGE2";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma EEE. dd MMM, yyyy");

    @Async
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void scheduleTask() throws InterruptedException, JsonProcessingException {
        LocalDateTime now = LocalDateTime.now();
        String formatDateTime = now.format(formatter);

        Map<String, List<ProductData>> productsData = new HashMap<>();

        if(appProperties.getStocks().isEmpty()){
            log.error("No stocks found in the application properties");
            return;
        }

        productsData.put(exchange1, getCachedProductsData(exchange1, formatDateTime));
        productsData.put(exchange2, getCachedProductsData(exchange2, formatDateTime));

        messagingTemplate.convertAndSend("/queue/market-data", productsData);

        log.info("Sent products data {} to WebSocket at {}", productsData, formatDateTime);
    }

    private List<ProductData> getCachedProductsData(String exchange, String formatDateTime) {
        List<ProductData> products = new ArrayList<>();
        appProperties.getStocks().forEach(stock -> {
            var cachedJsonProduct = redisTemplate.opsForValue().get(exchange + ":" + stock);

            if (cachedJsonProduct != null && !cachedJsonProduct.isEmpty()) {
                log.info("Retrieved cached product JSON: {}", cachedJsonProduct);

                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    ProductData productData = objectMapper.readValue(cachedJsonProduct, ProductData.class);
                    products.add(productData);
                } catch (JsonProcessingException e) {
                    log.error("Failed to deserialize JSON: {}", cachedJsonProduct, e);
                }
            } else {
                log.warn("No cached product data found for key {}:{} at {}", exchange, stock, formatDateTime);
            }
        });
        return products;
    }
}