package com.mms.order.manager.services.impl;


import com.mms.order.manager.exceptions.ExchangeException;
import com.mms.order.manager.dtos.requests.CreateOrderDto;
import com.mms.order.manager.dtos.responses.GetOrderDto;
import com.mms.order.manager.enums.ExecutionMode;
import com.mms.order.manager.enums.OrderStatus;
import com.mms.order.manager.exceptions.MarketDataException;
import com.mms.order.manager.exceptions.OrderException;
import com.mms.order.manager.repositories.ProductRepository;
import com.mms.order.manager.repositories.UserRepository;
import com.mms.order.manager.utils.factories.OrderExecutionServiceFactory;
import com.mms.order.manager.utils.factories.OrderValidatorFactory;
import com.mms.order.manager.utils.mappers.OrderMapper;
import com.mms.order.manager.models.Order;
import com.mms.order.manager.services.interfaces.*;
import com.mms.order.manager.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ExchangeExecutor exchangeExecutor;
    private final OrderValidatorFactory orderValidatorFactory;
    private final OrderExecutionServiceFactory orderExecutionServiceFactory;

    @Override
    @Transactional
    public void createOrder(CreateOrderDto orderDto) throws OrderException, MarketDataException, ExchangeException {
        var orderValidator = orderValidatorFactory.getOrderValidator(orderDto.executionMode());
        orderValidator.validateOrder(orderDto);

        var order = orderMapper.toOrder(orderDto);
        orderRepository.save(order);

        boolean isExecuted = executeOrder(order, orderDto);

        if (!isExecuted) {
            throw new ExchangeException("Could not execute on the exchange");
        }
    }

    private boolean executeOrder(Order order, CreateOrderDto orderDto) throws ExchangeException, OrderException {
        if (ExecutionMode.INSTANT == orderDto.executionMode()) {
            return exchangeExecutor.executeOrder(order, orderDto.preferredExchangeSlug(), 1.0);
        } else {
            var orderExecutionService = orderExecutionServiceFactory.getOrderExecutionService(order.getExecutionMode());
            return orderExecutionService.executeOrder(order);
        }
    }

    @Override
    public Optional<GetOrderDto> getOrder(long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isEmpty()) {
            return Optional.empty();
        }

        var order = optionalOrder.get();

        if (order.getStatus() == OrderStatus.PENDING) {
            // TODO
            updateOrderStatus();
        }

        return Optional.empty();
    }

    @Override
    public boolean cancelOrder(long orderId) {
        return false;
    }

    private void updateOrderStatus() {

    }


}
