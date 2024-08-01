package com.mms.order.manager.services.impl;


import com.mms.order.manager.exceptions.ExchangeException;
import com.mms.order.manager.dtos.requests.CreateOrderDto;
import com.mms.order.manager.exceptions.MarketDataException;
import com.mms.order.manager.exceptions.OrderException;
import com.mms.order.manager.utils.converters.OrderConvertor;
import com.mms.order.manager.models.Order;
import com.mms.order.manager.services.interfaces.*;
import com.mms.order.manager.repositories.OrderRepository;
import com.mms.order.manager.utils.OrderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final OrderExecutionService orderExecutionService;
    private final OrderConvertor orderConvertor;

    @Override
    @Transactional
    public void createOrder(CreateOrderDto orderDto) throws OrderException, MarketDataException, ExchangeException {
        orderValidator.validateOrder(orderDto);

        Order order = orderConvertor.convert(orderDto);
        orderRepository.save(order);

        orderExecutionService.executeOrder(order, orderDto);
    }
}
