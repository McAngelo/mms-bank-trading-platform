package com.mms.market_data_service.tasks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mms.market_data_service.dtos.cache.OrderBookSummary;
import com.mms.market_data_service.dtos.responses.OrderBookDto;
import com.mms.market_data_service.dtos.responses.ProductData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mms.market_data_service.enums.OrderSide;
import com.mms.market_data_service.enums.OrderType;
import com.mms.market_data_service.exceptions.ExchangeException;
import com.mms.market_data_service.services.interfaces.ExchangeService;
import com.mms.market_data_service.services.interfaces.RedisService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledTasks {
    private final ExchangeService exchangeService;
    private final RedisService redisService;
    private final ObjectMapper objectMapper;
    private List<String> exchanges =  List.of("EXCHANGE1", "EXCHANGE2");

    private static final Duration CACHE_EXPIRY_DURATION = Duration.ofHours(1);

    @PostConstruct
    public void onStartup() throws ExchangeException {
        populateCacheForExchange("EXCHANGE1");
        populateCacheForExchange("EXCHANGE2");
        cacheOrderBook();
        log.info("Populated cache on startup");
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void updateProductDataCache() {
        exchanges.forEach(this::populateCacheForExchange);
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
            redisService.deleteKey(key);

            for (ProductData product : products) {
                redisService.addToList(key,  objectMapper.writeValueAsString(product));
            }

            redisService.setTTL(key, CACHE_EXPIRY_DURATION);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize product data for {}: {}", exchange, e.getMessage(), e);
        } catch (Exception e) {
            log.error("Unexpected error while caching product data for {}: {}", exchange, e.getMessage(), e);
        }
    }

    public void cacheOrderBook () throws ExchangeException {
        List<String> products =  List.of("IBM", "TSLA", "GOOGL", "MSFT", "NFLX", "AAPL", "ORCL", "AMZN");

        for (String exchangeSlug : exchanges) {
            for (String product: products) {
                List<OrderBookDto> orderBookDtoList = exchangeService.getOrderBookFromExchange(exchangeSlug, Optional.of(product), Optional.of("open"));

                Map<OrderSide, Long> orderQuantities = orderBookDtoList.stream()
                        .filter(order -> OrderType.LIMIT == order.orderType())
                        .collect(Collectors.groupingBy(
                                OrderBookDto::side,
                                Collectors.summingLong(OrderBookDto::quantity)
                        ));

                long totalBuyQuantity = orderQuantities.getOrDefault(OrderSide.BUY, 0L);
                long totalSellQuantity = orderQuantities.getOrDefault(OrderSide.SELL, 0L);

                try {
                    redisService.addToHash(
                            String.format("orderbook:%s", exchangeSlug),
                            product,
                            objectMapper.writeValueAsString(new OrderBookSummary(product, totalBuyQuantity, totalSellQuantity))
                    );
                } catch (JsonProcessingException e) {
                    log.error("Failed to serialize order book for {}: {}", exchangeSlug, e.getMessage(), e);
                }
            }
        }
    }
}
