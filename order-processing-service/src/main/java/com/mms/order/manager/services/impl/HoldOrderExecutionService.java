package com.mms.order.manager.services.impl;

import com.mms.order.manager.models.Order;
import com.mms.order.manager.repositories.ExchangeRepository;
import com.mms.order.manager.services.interfaces.MarketDataService;
import com.mms.order.manager.services.interfaces.OrderExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HoldOrderExecutionService implements OrderExecutionService {
    private final MarketDataService marketDataService;
    private final ExchangeExecutor exchangeExecutor;

    @Override
    public boolean executeOrder(Order order) {
        // Hold based on trend
        return false;
    }
}
