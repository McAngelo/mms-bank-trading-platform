package com.mms.order.manager.services.impl;

import com.mms.order.manager.dtos.requests.CreateOrderDto;
import com.mms.order.manager.enums.ExecutionMode;
import com.mms.order.manager.exceptions.ExchangeException;
import com.mms.order.manager.models.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderExecutionService {
    private final NonSplitOrderExecutionService nonSplitOrderExecutionService;
    private final BestPriceExecutionStrategyServiceImpl bestPriceSplitStrategyService;
    private final HoldExecutionStrategyServiceImpl holdSplitExecutionStrategyService;

    public void executeOrder(Order order, CreateOrderDto orderDto) throws ExchangeException {
        if (isSingleExchangeOrder(order.getExecutionMode())) {
            nonSplitOrderExecutionService.executeOrder(order, orderDto.preferredExchangeSlug());
        }

        if (shouldHoldOrder()) {
            bestPriceSplitStrategyService.executeOrder(order);
        } else {
            holdSplitExecutionStrategyService.executeOrder(order);
        }

    }

    private boolean shouldHoldOrder() {
        return false;
    }

    private boolean isSingleExchangeOrder(ExecutionMode executionMode) {
        return ExecutionMode.SINGLE_EXCHANGE == executionMode;
    }

}
