package com.mms.order.manager.services.impl;


import com.mms.order.manager.exceptions.ExchangeException;
import com.mms.order.manager.models.Order;
import com.mms.order.manager.services.interfaces.MarketDataService;
import com.mms.order.manager.services.interfaces.OrderSplittingStrategyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HoldStrategyServiceImpl implements OrderSplittingStrategyService {
    private final MarketDataService marketDataService;

    @Override
    public void executeOrder(Order order) throws ExchangeException {
        // TODO: Hold based on trend
    }
}
