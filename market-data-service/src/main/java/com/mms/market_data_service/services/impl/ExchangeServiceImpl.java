package com.mms.market_data_service.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mms.market_data_service.dtos.responses.ProductData;
import com.mms.market_data_service.exceptions.ExchangeException;
import com.mms.market_data_service.dtos.responses.OrderBookDto;
import com.mms.market_data_service.services.interfaces.ExchangeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${external.exchange1.base-url}")
    private String EXCHANGE1_BASE_URL;

    @Value("${external.exchange2.base-url}")
    private String EXCHANGE2_BASE_URL;

    @Override
    public List<OrderBookDto> getOrderBookFromExchange(
            String exchange,
            Optional<String> product,
            Optional<String> specifier
    ) throws ExchangeException
    {
        var exchangeBaseUrl = getExchangeBaseUrl(exchange)
                .orElseThrow(() ->  new ExchangeException("Could not determine exchange from request"));

        var urlBuilder = new StringBuilder(exchangeBaseUrl);

        urlBuilder.append("/").append("orderbook");
        product.ifPresent(p -> urlBuilder.append("/").append(p));
        specifier.ifPresent(s -> urlBuilder.append("/").append(s));

        String url = urlBuilder.toString();
        try {
            var response = restTemplate.getForEntity(url, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new ExchangeException("Failed to fetch order book data from exchange");
            }

            return objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        } catch (Exception e) {
            throw new ExchangeException("Could not parse order book from exchange", e);
        }
    }

    @Override
    public List<ProductData> getProductDataFromExchange(String exchange) throws ExchangeException {
        var exchangeBaseUrl = getExchangeBaseUrl(exchange)
                .orElseThrow(() -> new ExchangeException("Could not determine exchange"));

        var url = exchangeBaseUrl + "/pd";

        try {
            var response = restTemplate.getForEntity(url, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new ExchangeException("Failed to fetch product data from exchange");
            }

            return objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        } catch (Exception e) {
            throw new ExchangeException("Could not parse product data from exchange" + e);
        }
    }

    private Optional<String> getExchangeBaseUrl(String exchange) {
        if (exchange.equals("EXCHANGE1")) {
            return Optional.of(EXCHANGE1_BASE_URL);

        } else if(exchange.equals("EXCHANGE2")) {
            return Optional.of(EXCHANGE2_BASE_URL);

        } else {
            return Optional.empty();
        }
    }
}
