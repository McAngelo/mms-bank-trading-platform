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
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrderConvertor implements Converter<CreateOrderDto, Order> {

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

    public GetOrderDto convertToGetOrderDto(Order order, List<OrderSplit> orderSplits, List<Execution> executions) {
        return GetOrderDto.builder()
                .quantity(order.getQuantity())
                .side(order.getSide())
                .orderType(order.getType())
                .price(order.getPrice())
                .ticker(order.getTicker())
                .executions(executions
                        .stream()
                        .map(e -> new ExecutionDto(e.getDateTime(), e.getQuantity(), e.getPrice()))
                        .toList()
                )
                .orderSplits(orderSplits
                        .stream()
                        .map(os -> new OrderSplitDto(os.getExchange().getName(), os.getQuantity()))
                        .toList()
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
