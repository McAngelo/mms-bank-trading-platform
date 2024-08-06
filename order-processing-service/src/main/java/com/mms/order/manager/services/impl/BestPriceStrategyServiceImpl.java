package com.mms.order.manager.services.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mms.order.manager.dtos.cache.OrderBookSummary;
import com.mms.order.manager.dtos.internal.CreateExchangeOrderDto;
import com.mms.order.manager.dtos.internal.OpenOrderDto;
import com.mms.order.manager.dtos.internal.ProductMarketData;
import com.mms.order.manager.enums.OrderSide;
import com.mms.order.manager.enums.OrderType;
import com.mms.order.manager.exceptions.ExchangeException;
import com.mms.order.manager.helpers.JsonHelper;
import com.mms.order.manager.models.Exchange;
import com.mms.order.manager.models.Order;
import com.mms.order.manager.models.OrderSplit;
import com.mms.order.manager.repositories.ExchangeRepository;
import com.mms.order.manager.repositories.OrderSplitRepository;
import com.mms.order.manager.services.interfaces.MarketDataService;
import com.mms.order.manager.services.interfaces.OrderSplittingStrategyService;
import com.mms.order.manager.services.interfaces.RedisService;
import com.mms.order.manager.utils.converters.OrderConvertor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class BestPriceStrategyServiceImpl implements OrderSplittingStrategyService {
    private final ExchangeServiceImpl exchangeService;
    private final OrderSplitRepository orderSplitRepository;
    private final SingleOrderStrategyService singleOrderStrategyService;
    private final MarketDataService marketDataService;
    private final ExchangeRepository exchangeRepository;
    private final OrderConvertor orderConvertor;
    private final RedisService redisService;

    @Override
    public void executeOrder(Order order) throws ExchangeException {
        String ticker = order.getTicker();
        OrderSide orderSide = order.getSide();

        Map<String, ProductMarketData> productDataMap = marketDataService.getProductDataFromAllExchanges(ticker);

        List<Pair<String, ProductMarketData>> sortedProductDataPairs = productDataMap.entrySet().stream()
                .sorted((entry1, entry2) -> {
                    double price1 = entry1.getValue().lastTradedPrice();
                    double price2 = entry2.getValue().lastTradedPrice();
                    return OrderSide.BUY.equals(orderSide) ? Double.compare(price1, price2) : Double.compare(price2, price1);
                })
                .map(entry -> Pair.of(entry.getKey(), entry.getValue()))
                .toList();

        String bestExchangeSlug = sortedProductDataPairs.getFirst().getLeft();
        String secondBestExchangeSlug = sortedProductDataPairs.get(1).getKey();

        try {
            OrderBookSummary primaryExchangeOrderBookSummary = JsonHelper.fromJson(
                    (String) redisService.getFromHash(String.format("orderbook:%s", bestExchangeSlug), ticker),
                    OrderBookSummary.class
            );

            OrderBookSummary secondaryExchangeOrderBookSummary = JsonHelper.fromJson(
                    (String) redisService.getFromHash(String.format("orderbook:%s", secondBestExchangeSlug), ticker),
                    OrderBookSummary.class
            );

            processOrderExecution(order, orderSide, primaryExchangeOrderBookSummary, secondaryExchangeOrderBookSummary, sortedProductDataPairs, bestExchangeSlug);
        } catch (JsonProcessingException e) {
            log.error(e.toString());
        }
    }

    private void processOrderExecution(
            Order order, OrderSide orderSide,
            OrderBookSummary primarySummary, OrderBookSummary secondarySummary,
            List<Pair<String, ProductMarketData>> sortedProductDataPairs,
            String bestExchangeSlug) throws ExchangeException {

        long primaryOppositeQuantity = getOppositeOrderQuantity(orderSide, primarySummary);
        long secondaryOppositeQuantity = getOppositeOrderQuantity(orderSide, secondarySummary);

        if (primaryOppositeQuantity == 0 && secondaryOppositeQuantity == 0) {
            singleOrderStrategyService.executeOrder(order, bestExchangeSlug);
        } else if (primaryOppositeQuantity > 0 && secondaryOppositeQuantity == 0) {
            singleOrderStrategyService.executeOrder(order, bestExchangeSlug);
        } else if (primaryOppositeQuantity == 0 && secondaryOppositeQuantity > 0) {
            splitOrdersBasedOnOpenOrders(order, secondarySummary, sortedProductDataPairs);
        } else {
            splitOrdersBasedOnOpenOrders(order, primarySummary, sortedProductDataPairs);
        }
    }
    private long getOppositeOrderQuantity(OrderSide orderSide, OrderBookSummary orderBookSummary) {
        if (OrderSide.BUY == orderSide) {
            return orderBookSummary.getTotalSellQuantity();
        } else {
            return orderBookSummary.getTotalBuyQuantity();
        }
    }

    private void splitOrdersBasedOnOpenOrders(
            Order order,
            OrderBookSummary orderBookSummary,
            List<Pair<String, ProductMarketData>> sortedProductDataPairs
    ) throws ExchangeException {

        int originalQuantity = order.getQuantity();
        int remainingQuantity = originalQuantity;
        int availableQuantity = (int) getOppositeOrderQuantity(order.getSide(), orderBookSummary);

        for (Pair<String, ProductMarketData> exchangePair : sortedProductDataPairs) {
            if (remainingQuantity <= 0) break;

            Exchange exchange = getExchange(exchangePair.getLeft());

            int orderSplitQuantity = (remainingQuantity == originalQuantity)
                    ? (int) Math.round(originalQuantity * 0.80)
                    : remainingQuantity;

            int quantityToOrder = Math.min(orderSplitQuantity, availableQuantity);

            OrderSplit orderSplit = OrderSplit.builder()
                    .order(order)
                    .quantity(quantityToOrder)
                    .isExecuted(false)
                    .exchange(exchange)
                    .build();

            String exchangeOrderId = exchangeService.executeOrder(orderConvertor.convert(orderSplit), exchange);
            orderSplit.setExchangeOrderId(exchangeOrderId);

            orderSplitRepository.save(orderSplit);
            remainingQuantity -= quantityToOrder;
            availableQuantity -= quantityToOrder;
        }
    }

    private Exchange getExchange(String exchangeSlug) throws ExchangeException {
        Optional<Exchange> optionalExchange = exchangeRepository.findBySlug(exchangeSlug);

        if (optionalExchange.isEmpty()) {
            throw new ExchangeException("Invalid exchange slug");
        }

        return optionalExchange.get();
    }
}
