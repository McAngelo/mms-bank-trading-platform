package com.mms.order.manager.services.impl;

import com.mms.order.manager.dtos.internal.CreateExchangeOrderDto;
import com.mms.order.manager.dtos.internal.UpdatedOrderDto;
import com.mms.order.manager.exceptions.ExchangeException;
import com.mms.order.manager.models.Exchange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExchangeServiceImpl {
    private final RestTemplate restTemplate;
    private final RedisTemplate<String, Serializable> redisTemplate;

    public String executeOrder(CreateExchangeOrderDto exchangeOrderDto, Exchange exchange) throws ExchangeException {
        String url = buildExchangeUrl(exchange);

        ResponseEntity<String> response = restTemplate.postForEntity(url, exchangeOrderDto, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ExchangeException("Failed to fetch order book data from exchange");
        }

        log.info("Successfully place order on exchange");

        String exchangeOrderId = response.getBody();
        storeOrderInCache(exchangeOrderId, exchange.getSlug());

        return exchangeOrderId;
    }

    public UpdatedOrderDto getOrderStatusFromExchange(String exchangeOrderId, Exchange exchange) throws ExchangeException {
        String url = buildExchangeUrl(exchange);

        exchangeOrderId = exchangeOrderId.replace("\"", "");

        url = String.format("%s/%s", url, exchangeOrderId);

        log.warn(url);

        ResponseEntity<UpdatedOrderDto> response = restTemplate.getForEntity(url, UpdatedOrderDto.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ExchangeException("Failed to fetch order book data from exchange");
        }

        log.info("Successfully retrieved order status from exchange");


        return response.getBody();
    }

    private void storeOrderInCache(String exchangeOrderId, String exchangeSlug) {
        redisTemplate.opsForList().rightPush(
                String.format("pending-orders:%s", exchangeSlug),
                exchangeOrderId
        );
    }

    private String buildExchangeUrl(Exchange exchange) {
        return exchange.getBaseUrl() + "/" + exchange.getPrivateKey() + "/" + "order";
    }


}
