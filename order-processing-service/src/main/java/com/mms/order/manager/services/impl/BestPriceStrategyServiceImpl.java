package com.mms.order.manager.services.impl;

import com.mms.order.manager.dtos.internal.CreateExchangeOrderDto;
import com.mms.order.manager.dtos.internal.OpenOrderDto;
import com.mms.order.manager.dtos.internal.ProductMarketData;
import com.mms.order.manager.enums.OrderSide;
import com.mms.order.manager.enums.OrderType;
import com.mms.order.manager.exceptions.ExchangeException;
import com.mms.order.manager.models.Exchange;
import com.mms.order.manager.models.Order;
import com.mms.order.manager.models.OrderSplit;
import com.mms.order.manager.repositories.ExchangeRepository;
import com.mms.order.manager.repositories.OrderSplitRepository;
import com.mms.order.manager.services.interfaces.MarketDataService;
import com.mms.order.manager.services.interfaces.OrderSplittingStrategyService;
import com.mms.order.manager.utils.converters.OrderConvertor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

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

        List<OpenOrderDto> bestExchangeOpenOrders = marketDataService.getOpenOrdersFromOrderBook(bestExchangeSlug, ticker)
                .stream()
                .filter(openOrder -> !orderSide.toString().equalsIgnoreCase(openOrder.side()))
                .toList();

        List<OpenOrderDto> secondBestExchangeOpenOrders = marketDataService.getOpenOrdersFromOrderBook(secondBestExchangeSlug, ticker)
                .stream()
                .filter(openOrder -> !orderSide.toString().equalsIgnoreCase(openOrder.side()))
                .toList();

        if (bestExchangeOpenOrders.isEmpty() && secondBestExchangeOpenOrders.isEmpty()) {
            singleOrderStrategyService.executeOrder(order, bestExchangeSlug);
            return;
        } else if (secondBestExchangeOpenOrders.isEmpty()) {
            singleOrderStrategyService.executeOrder(order, bestExchangeSlug);
            return;
        }

        splitOrdersBasedOnOpenOrders(order, bestExchangeOpenOrders, sortedProductDataPairs);
    }

    private void splitOrdersBasedOnOpenOrders(
            Order order,
            List<OpenOrderDto> bestExchangeOpenOrders,
            List<Pair<String, ProductMarketData>> sortedProductDataPairs
    ) throws ExchangeException {

        int remainingQuantity = order.getQuantity();
        int availableQuantity = calculateAvailableQuantity(bestExchangeOpenOrders, order.getTicker(), order.getSide());


        for (Pair<String, ProductMarketData> exchangePair : sortedProductDataPairs) {
            Exchange exchange = getExchange(exchangePair.getLeft());

            OrderSplit orderSplit = OrderSplit.builder()
                    .order(order)
                    .quantity(remainingQuantity)
                    .isExecuted(false)
                    .exchange(exchange)
                    .build();

            exchangeService.executeOrder(orderConvertor.convert(orderSplit), exchange);
            orderSplitRepository.save(orderSplit);
            remainingQuantity -= orderSplit.getQuantity();
        }
    }


    private int calculateAvailableQuantity(List<OpenOrderDto> openOrders, String ticker, OrderSide orderSide) {
        return openOrders.stream()
                .filter(openOrder -> !orderSide.toString().equalsIgnoreCase(openOrder.side())
                        && openOrder.product().equalsIgnoreCase(ticker))
                .mapToInt(OpenOrderDto::quantity)
                .sum();
    }

    private Exchange getExchange(String exchangeSlug) throws ExchangeException {
        Optional<Exchange> optionalExchange = exchangeRepository.findBySlug(exchangeSlug);

        if (optionalExchange.isEmpty()) {
            throw new ExchangeException("Invalid exchange slug");
        }

        return optionalExchange.get();
    }
}
