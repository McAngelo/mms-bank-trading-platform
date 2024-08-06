package com.mms.order.manager.services.impl;

import com.mms.order.manager.dtos.requests.CreateOrderDto;
import com.mms.order.manager.enums.ExecutionMode;
import com.mms.order.manager.exceptions.ExchangeException;
import com.mms.order.manager.models.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExecutionStrategyService {
    private final SingleOrderStrategyService singleOrderStrategyService;
    private final BestPriceStrategyServiceImpl bestPriceSplitStrategyService;

    public void executeOrder(Order order, CreateOrderDto orderDto) throws ExchangeException {
        if (ExecutionMode.SINGLE_EXCHANGE == order.getExecutionMode()) {
            singleOrderStrategyService.executeOrder(order, orderDto.preferredExchangeSlug());
        } else {
            bestPriceSplitStrategyService.executeOrder(order);
        }
    }
}
