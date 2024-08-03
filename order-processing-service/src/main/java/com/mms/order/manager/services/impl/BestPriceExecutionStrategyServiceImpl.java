package com.mms.order.manager.services.impl;

import com.mms.order.manager.dtos.internal.CreateExchangeOrderDto;
import com.mms.order.manager.dtos.internal.OpenOrderDto;
import com.mms.order.manager.dtos.internal.ProductMarketData;
import com.mms.order.manager.enums.OrderType;
import com.mms.order.manager.exceptions.ExchangeException;
import com.mms.order.manager.models.Exchange;
import com.mms.order.manager.models.Order;
import com.mms.order.manager.models.OrderSplit;
import com.mms.order.manager.repositories.ExchangeRepository;
import com.mms.order.manager.repositories.OrderSplitRepository;
import com.mms.order.manager.services.interfaces.MarketDataService;
import com.mms.order.manager.services.interfaces.OrderSplittingStrategyService;
import com.mms.order.manager.utils.ExchangeExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class BestPriceExecutionStrategyServiceImpl implements OrderSplittingStrategyService {
    private final OrderSplitRepository splitOrderSplitRepository;
    private final NonSplitOrderExecutionService nonSplitOrderExecutionService;
    private final MarketDataService marketDataService;
    private final ExchangeExecutor exchangeExecutor;
    private final ExchangeRepository exchangeRepository;

    @Override
    public void executeOrder(Order order) throws ExchangeException {
        String ticker = order.getTicker();

        Map<String, ProductMarketData> productDataMap = marketDataService.getProductDataFromAllExchanges(ticker);

        List<Pair<String, ProductMarketData>> sortedProductDataPairs = productDataMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.comparing(ProductMarketData::lastTradedPrice)))
                .map(entry -> Pair.of(entry.getKey(), entry.getValue()))
                .toList();

        List<OpenOrderDto> openOrders = getOpenOrdersFromOrderBook(ticker);

        if (openOrders.isEmpty()) {
            nonSplitOrderExecutionService.executeOrder(order, sortedProductDataPairs.getFirst().getLeft());
        }

        for (Pair<String, ProductMarketData> exchangeProductDataPair : sortedProductDataPairs) {
            CreateExchangeOrderDto newExchangeOrder = buildCreateExchangeOrderDto(order);

            Exchange exchange = getExchange(exchangeProductDataPair.getLeft());

            String exchangeOrderId = exchangeExecutor.executeOrder(newExchangeOrder, exchange);

            splitOrderSplitRepository.save(OrderSplit.builder()
                    .order(order)
                    .exchange(exchange)
                    .quantity(newExchangeOrder.quantity())
                    .exchangeOrderId(exchangeOrderId)
                    .build()
            );
        }

    }


    private CreateExchangeOrderDto buildCreateExchangeOrderDto(Order order) {
        CreateExchangeOrderDto.CreateExchangeOrderDtoBuilder newExchangeOrderBuilder = CreateExchangeOrderDto.builder()
                .product(order.getTicker())
                .quantity(order.getQuantity())
                .side(order.getSide().toString())
                .type(order.getType().toString());

        if (OrderType.LIMIT == order.getType()) {
            newExchangeOrderBuilder.price(order.getPrice());
        }

        return newExchangeOrderBuilder.build();
    }

    private List<OpenOrderDto> getOpenOrdersFromOrderBook(String ticker) {
        return new ArrayList<>();
    }

    private Exchange getExchange(String exchangeSlug) throws ExchangeException {
        Optional<Exchange> optionalExchange = exchangeRepository.findBySlug(exchangeSlug);

        if (optionalExchange.isEmpty()) {
            throw new ExchangeException("Invalid exchange slug");
        }

        return optionalExchange.get();
    }
}
