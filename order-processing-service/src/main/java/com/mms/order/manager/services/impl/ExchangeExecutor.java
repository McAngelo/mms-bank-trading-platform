package com.mms.order.manager.services.impl;

import com.mms.order.manager.dtos.internal.CreateExchangeOrderDto;
import com.mms.order.manager.enums.OrderType;
import com.mms.order.manager.exceptions.ExchangeException;
import com.mms.order.manager.models.Order;
import com.mms.order.manager.models.OrderExchange;
import com.mms.order.manager.repositories.ExchangeRepository;
import com.mms.order.manager.repositories.OrderExchangeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
@RequiredArgsConstructor
public class ExchangeExecutor {
    private final RestTemplate restTemplate;
    private final ExchangeRepository exchangeRepository;
    private final OrderExchangeRepository orderExchangeRepository;

    public boolean executeOrder(Order order, String exchangeSlug, double percentage) throws ExchangeException {
        var optionalExchange = exchangeRepository.findBySlug(exchangeSlug);

        if (optionalExchange.isEmpty()) {
            throw new ExchangeException("Invalid exchange slug");
        }

        var exchange = optionalExchange.get();

        var url = exchange.getBaseUrl() + "/" + exchange.getPrivateKey() + "/" + "order";

        int originalQuantity = order.getQuantity();

        double newQuantityDouble = originalQuantity * percentage;
        int newQuantity = (int) Math.round(newQuantityDouble);

        var newExchangeOrder = CreateExchangeOrderDto.builder()
                .product(order.getProduct().getSymbol())
                .quantity(newQuantity)
                .side(order.getSide().toString())
                .type(order.getType().toString());

        if (OrderType.LIMIT == order.getType()) {
            newExchangeOrder.price(order.getPrice());
        }

        var response = restTemplate.postForEntity(url, newExchangeOrder.build(), String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ExchangeException("Failed to fetch order book data from exchange");
        }

        log.info("Successfully place order on exchange");

        var exchangeOrderId = response.getBody();

        orderExchangeRepository.save(OrderExchange.builder()
                .order(order)
                .exchange(exchange)
                .quantity(newQuantity)
                .exchangeOrderId(exchangeOrderId)
                .build()
        );

        return true;
    }
}
