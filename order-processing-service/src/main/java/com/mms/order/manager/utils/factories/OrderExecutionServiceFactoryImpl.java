package com.mms.order.manager.utils.factories;

import com.mms.order.manager.enums.ExecutionMode;
import com.mms.order.manager.exceptions.OrderException;
import com.mms.order.manager.services.impl.BestPriceOrderExecutionServiceImpl;
import com.mms.order.manager.services.impl.ExchangeExecutor;
import com.mms.order.manager.services.impl.HoldOrderExecutionService;
import com.mms.order.manager.services.interfaces.MarketDataService;
import com.mms.order.manager.services.interfaces.OrderExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderExecutionServiceFactoryImpl implements OrderExecutionServiceFactory {
    private final MarketDataService marketDataService;
    private final ExchangeExecutor exchangeExecutor;

    @Override
    public OrderExecutionService getOrderExecutionService(ExecutionMode executionMode) throws OrderException {
        return switch (executionMode) {
            case BEST_PRICE -> new BestPriceOrderExecutionServiceImpl(marketDataService, exchangeExecutor);
            case HOLD -> new HoldOrderExecutionService(marketDataService, exchangeExecutor);
            default -> throw new OrderException("Unsupported execution mode: " + executionMode);
        };
    }
}
