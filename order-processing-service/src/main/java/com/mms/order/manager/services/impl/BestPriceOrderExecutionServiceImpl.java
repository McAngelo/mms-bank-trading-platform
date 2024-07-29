package com.mms.order.manager.services.impl;

import com.mms.order.manager.dtos.internal.ProductData;
import com.mms.order.manager.exceptions.ExchangeException;
import com.mms.order.manager.models.Order;
import com.mms.order.manager.services.interfaces.MarketDataService;
import com.mms.order.manager.services.interfaces.OrderExecutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class BestPriceOrderExecutionServiceImpl implements OrderExecutionService {
    private final MarketDataService marketDataService;
    private final ExchangeExecutor exchangeExecutor;
    private final List<Double> SPLIT_RATIOS = List.of(0.7, 0.3);

    @Override
    public boolean executeOrder(Order order) throws ExchangeException {
        var ticker = order.getProduct().getSymbol();

        var productDataMap = marketDataService.getProductDataFromAllExchanges(ticker);

        var sortedProductDataPairs = productDataMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.comparing(ProductData::lastTradedPrice)))
                .map(entry -> Pair.of(entry.getKey(), entry.getValue()))
                .toList();

        if (SPLIT_RATIOS.size() > sortedProductDataPairs.size()) {
            throw new IllegalArgumentException("More split ratios provided than available exchanges.");
        }

        for (int i = 0; i < sortedProductDataPairs.size(); i ++) {
            var exchangeProductDataPair = sortedProductDataPairs.get(i);
            var exchangeSlug = exchangeProductDataPair.getLeft();

            var status = exchangeExecutor.executeOrder(order, exchangeSlug, SPLIT_RATIOS.get(i));

            if (!status) {
                log.error("Order execution failed for exchange: {}", exchangeSlug);
                return false;
            }
        }

        return true;
    }
}
