package com.mms.order.manager.utils.converters;

import com.mms.order.manager.dtos.internal.CreateExchangeOrderDto;
import com.mms.order.manager.dtos.internal.ExecutionDto;
import com.mms.order.manager.dtos.requests.CreateOrderDto;
import com.mms.order.manager.dtos.responses.GetOrderDto;
import com.mms.order.manager.dtos.responses.GetOrdersDto;
import com.mms.order.manager.dtos.responses.OrderSplitDto;
import com.mms.order.manager.enums.OrderStatus;
import com.mms.order.manager.enums.OrderType;
import com.mms.order.manager.models.Execution;
import com.mms.order.manager.models.Order;
import com.mms.order.manager.models.OrderSplit;
import com.mms.order.manager.repositories.ExecutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderConvertor implements Converter<CreateOrderDto, Order> {
    private final ExecutionConvertor executionConvertor;
    private final ExecutionRepository executionRepository;

    @Override
    public Order convert(CreateOrderDto orderDto) {
        Order.OrderBuilder orderBuilder = Order.builder()
                .portfolioId(orderDto.portfolioId())
                .ticker(orderDto.ticker())
                .userId(orderDto.userId())
                .quantity(orderDto.quantity())
                .side(orderDto.side())
                .type(orderDto.type())
                .status(OrderStatus.PENDING)
                .executionMode(orderDto.executionMode())
                .executionMode(orderDto.executionMode());

        if (OrderType.LIMIT == orderDto.type()) {
            orderBuilder.price(orderDto.price());
        }

        return orderBuilder.build();
    }

    public CreateExchangeOrderDto convert(OrderSplit orderSplit) {
        CreateExchangeOrderDto.CreateExchangeOrderDtoBuilder exchangeOrderDtoBuilder = CreateExchangeOrderDto.builder()
                .product(orderSplit.getOrder().getTicker())
                .quantity(orderSplit.getQuantity())
                .side(orderSplit.getOrder().getSide().toString())
                .type(orderSplit.getOrder().getType().toString());

        if (OrderType.LIMIT == orderSplit.getOrder().getType()) {
            exchangeOrderDtoBuilder.price(orderSplit.getOrder().getPrice());
        }

        return exchangeOrderDtoBuilder.build();
    }

    public CreateExchangeOrderDto convert(Order order) {
        CreateExchangeOrderDto.CreateExchangeOrderDtoBuilder exchangeOrderDtoBuilder = CreateExchangeOrderDto.builder()
                .product(order.getTicker())
                .quantity(order.getQuantity())
                .side(order.getSide().toString())
                .type(order.getType().toString());

        if (OrderType.LIMIT == order.getType()) {
            exchangeOrderDtoBuilder.price(order.getPrice());
        }

        return exchangeOrderDtoBuilder.build();
    }

    public GetOrderDto convertToGetOrderDto(Order order, List<OrderSplit> orderSplits) {
        return GetOrderDto.builder()
                .quantity(order.getQuantity())
                .side(order.getSide())
                .orderType(order.getType())
                .price(order.getPrice())
                .ticker(order.getTicker())
                .orderStatus(order.getStatus())
                .orderSplits(orderSplits
                        .stream()
                        .map(os -> OrderSplitDto.builder()
                                .exchangeName(os.getExchange().getName())
                                .quantity(os.getQuantity())
                                .executions(executionRepository.findAllByOrderSplitId(os.getId())
                                        .stream().
                                        map(executionConvertor::convert).
                                        toList()
                                ).build()).toList()
                )
                .build();
    }

    public GetOrdersDto convertToGetOrdersDto(Order order) {
        return GetOrdersDto.builder()
                .quantity(order.getQuantity())
                .side(order.getSide())
                .orderType(order.getType())
                .price(order.getPrice())
                .ticker(order.getTicker())
                .build();
    }
}
