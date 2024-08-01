package com.mms.order.manager.services.impl;


import com.mms.order.manager.exceptions.ExchangeException;
import com.mms.order.manager.models.Order;
import com.mms.order.manager.services.interfaces.MarketDataService;
import com.mms.order.manager.services.interfaces.OrderSplittingStrategyService;
import com.mms.order.manager.utils.ExchangeExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HoldExecutionStrategyServiceImpl implements OrderSplittingStrategyService {
    private final MarketDataService marketDataService;
    private final ExchangeExecutor exchangeExecutor;

    @Override
    public void executeOrder(Order order) throws ExchangeException {
        // TODO: Hold based on trend
    }
}
