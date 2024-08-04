package com.mms.order.manager.services.impl;


import com.mms.order.manager.dtos.internal.ExecutionDto;
import com.mms.order.manager.dtos.internal.UpdatedOrderDto;
import com.mms.order.manager.dtos.responses.GetOrderDto;
import com.mms.order.manager.dtos.responses.GetOrdersDto;
import com.mms.order.manager.enums.OrderStatus;
import com.mms.order.manager.exceptions.ExchangeException;
import com.mms.order.manager.dtos.requests.CreateOrderDto;
import com.mms.order.manager.exceptions.MarketDataException;
import com.mms.order.manager.exceptions.OrderException;
import com.mms.order.manager.models.OrderSplit;
import com.mms.order.manager.repositories.ExecutionRepository;
import com.mms.order.manager.repositories.OrderSplitRepository;
import com.mms.order.manager.utils.converters.ExecutionConvertor;
import com.mms.order.manager.utils.converters.OrderConvertor;
import com.mms.order.manager.models.Order;
import com.mms.order.manager.services.interfaces.*;
import com.mms.order.manager.repositories.OrderRepository;
import com.mms.order.manager.utils.OrderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final ExecutionStrategyService executionStrategyService;
    private final OrderSplitRepository orderSplitRepository;
    private final ExecutionRepository executionRepository;
    private final OrderConvertor orderConvertor;
    private final ExchangeServiceImpl exchangeService;
    private final ExecutionConvertor executionConvertor;
    private final RedisServiceImpl redisServiceImpl;

    @Override
    @Transactional
    public void createOrder(CreateOrderDto orderDto) throws OrderException, MarketDataException, ExchangeException {
        orderValidator.validateOrder(orderDto);

        Order order = orderConvertor.convert(orderDto);
        orderRepository.save(order);

        executionStrategyService.executeOrder(order, orderDto);
    }

    @Override
    public GetOrderDto getOrder(long orderId) throws OrderException {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isEmpty()) {
            throw new OrderException("Order not found");
        }

        return orderConvertor.convertToGetOrderDto(
                optionalOrder.get(),
                orderSplitRepository.findAllByOrderId(orderId)
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

    @Override
    public void updateOrderStatus(long orderId) throws OrderException, ExchangeException {
        List<OrderSplit> orderSplits =  orderSplitRepository.findAllByOrderId(orderId);

        boolean anySplitUpdated = false;

        for (OrderSplit orderSplit : orderSplits) {
            if (!orderSplit.isExecuted()) {
                anySplitUpdated |= updateOrderSplitStatus(orderSplit);
            }
        }

        if (anySplitUpdated) {
            updateOrderStatusIfNeeded(orderId, Optional.of(orderSplits));
        }
    }

    @Override
    public void updateOrderStatus(String exchangeOrderId) throws OrderException, ExchangeException {
        OrderSplit orderSplit = orderSplitRepository.findByExchangeOrderId(exchangeOrderId)
                .orElseThrow(() -> new OrderException("Order split not found" + exchangeOrderId));

        if (!orderSplit.isExecuted() && updateOrderSplitStatus(orderSplit)) {
            updateOrderStatusIfNeeded(orderSplit.getOrder().getId(), Optional.empty());
        }
    }

    private boolean updateOrderSplitStatus(OrderSplit orderSplit) throws ExchangeException {
        UpdatedOrderDto updatedOrderDto = exchangeService.getOrderStatusFromExchange(
                orderSplit.getExchangeOrderId(),
                orderSplit.getExchange()
        );

        orderSplit.setQuantity(updatedOrderDto.quantity());

        int totalExecutionQuantity = updatedOrderDto.executions()
                .stream()
                .mapToInt(ExecutionDto::quantity)
                .sum();

        boolean isFullyExecuted = totalExecutionQuantity == updatedOrderDto.quantity();

        orderSplit.setExecuted(isFullyExecuted);

        orderSplitRepository.save(orderSplit);


        if (isFullyExecuted) {
            redisServiceImpl.removeFromSet(
                    String.format("pending-orders:%s", orderSplit.getExchange().getSlug()),
                    orderSplit.getExchangeOrderId()
            );
        }

        executionRepository.deleteAll();

        executionRepository.saveAll(
                updatedOrderDto.executions()
                        .stream()
                        .map(e -> executionConvertor.convert(e, orderSplit))
                        .toList()
        );

        return isFullyExecuted;
    }

    private void updateOrderStatusIfNeeded(long orderId, Optional<List<OrderSplit>> optionalOrderSplits) throws OrderException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException("Order not found"));

        List<OrderSplit> orderSplits;

        orderSplits = optionalOrderSplits.orElseGet(() -> orderSplitRepository.findAllByOrderId(orderId));

        int totalOrderSplitQuantity = orderSplits.stream()
                .mapToInt(OrderSplit::getQuantity)
                .sum();

        OrderStatus newStatus = totalOrderSplitQuantity == order.getQuantity()
                ? OrderStatus.FILLED
                : OrderStatus.PARTIALLY_FILLED;

        order.setStatus(newStatus);
        orderRepository.save(order);
    }
}
