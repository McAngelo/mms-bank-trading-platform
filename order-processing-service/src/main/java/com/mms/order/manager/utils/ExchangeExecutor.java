package com.mms.order.manager.utils;

import com.mms.order.manager.dtos.internal.CreateExchangeOrderDto;
import com.mms.order.manager.exceptions.ExchangeException;
import com.mms.order.manager.models.Exchange;
import com.mms.order.manager.repositories.ExchangeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
@Slf4j
@RequiredArgsConstructor
public class ExchangeExecutor {
    private final RestTemplate restTemplate;

    public String executeOrder(CreateExchangeOrderDto exchangeOrderDto, Exchange exchange) throws ExchangeException {
        String url = buildExchangeUrl(exchange);

        ResponseEntity<String> response = restTemplate.postForEntity(url, exchangeOrderDto, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ExchangeException("Failed to fetch order book data from exchange");
        }

        log.info("Successfully place order on exchange");
        return response.getBody();
    }

    private String buildExchangeUrl(Exchange exchange) {
        return exchange.getBaseUrl() + "/" + exchange.getPrivateKey() + "/" + "order";
    }
}
