package com.mms.market_data_service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mms.market_data_service.config.AppProperties;
import com.mms.market_data_service.config.Stock;
import com.mms.market_data_service.dtos.responses.ProductData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@EnableAsync
public class MarketDataWebSocketHandler extends TextWebSocketHandler {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private AppProperties appProperties;
    private static final String exchange1 = "EXCHANGE1";
    private static final String exchange2 = "EXCHANGE2";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private  WebSocketSession session;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    Random r = new Random();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.session = session;
        System.out.println("Connected to " + session.getRemoteAddress());

        scheduleTask();

    }



    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma EEE. dd MMM, yyyy");

    /*@Async
    @Scheduled(fixedRate = 3, timeUnit = TimeUnit.SECONDS)*/
    public void scheduleTask() throws InterruptedException, IOException {
        LocalDateTime now = LocalDateTime.now();
        String formatDateTime = now.format(formatter);

        Map<String, List<ProductData>> productsData = new HashMap<>();

        if(appProperties.getStocks().isEmpty()){
            log.error("No stocks found in the application properties");
            return;
        }

        Runnable task = () -> {
            try {

                productsData.put(exchange1, getCachedProductsData(exchange1, formatDateTime));
                productsData.put(exchange2, getCachedProductsData(exchange2, formatDateTime));

                messagingTemplate.convertAndSend("/queue/market-data", productsData);
                //Sending StockPrice
                TextMessage message = new TextMessage(objectMapper.writeValueAsString(productsData));
                this.session.sendMessage(message);
                sessions.add(this.session);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        scheduler.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);

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