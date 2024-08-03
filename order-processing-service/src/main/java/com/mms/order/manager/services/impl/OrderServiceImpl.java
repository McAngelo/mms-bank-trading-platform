package com.mms.order.manager.services.impl;


import com.mms.order.manager.dtos.responses.GetOrderDto;
import com.mms.order.manager.dtos.responses.GetOrdersDto;
import com.mms.order.manager.exceptions.ExchangeException;
import com.mms.order.manager.dtos.requests.CreateOrderDto;
import com.mms.order.manager.exceptions.MarketDataException;
import com.mms.order.manager.exceptions.OrderException;
import com.mms.order.manager.models.OrderSplit;
import com.mms.order.manager.repositories.ExecutionRepository;
import com.mms.order.manager.repositories.OrderSplitRepository;
import com.mms.order.manager.utils.converters.OrderConvertor;
import com.mms.order.manager.models.Order;
import com.mms.order.manager.services.interfaces.*;
import com.mms.order.manager.repositories.OrderRepository;
import com.mms.order.manager.utils.OrderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final OrderExecutionService orderExecutionService;
    private final OrderSplitRepository splitOrderSplitRepository;
    private final ExecutionRepository executionRepository;
    private final OrderConvertor orderConvertor;

    @Override
    @Transactional
    public void createOrder(CreateOrderDto orderDto) throws OrderException, MarketDataException, ExchangeException {
        orderValidator.validateOrder(orderDto);

        Order order = orderConvertor.convert(orderDto);
        orderRepository.save(order);

        orderExecutionService.executeOrder(order, orderDto);
    }

    @Override
    public GetOrderDto getOrder(long orderId) throws OrderException {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isEmpty()) {
            throw new OrderException("Order not found");
        }

        return orderConvertor.convertToGetOrderDto(
                optionalOrder.get(),
                splitOrderSplitRepository.findAllByOrderId(orderId),
                executionRepository.findAllByOrderId(orderId)
        );
    }

    @Override
    public List<GetOrdersDto> getOrders(long portfolioId, int page, int size) {
        Page<Order> orderPage = orderRepository.findByPortfolioId(
                portfolioId,
                PageRequest.of(page, size)
        );

        return orderPage.stream().map(orderConvertor::convertToGetOrdersDto).toList();

    }
}
