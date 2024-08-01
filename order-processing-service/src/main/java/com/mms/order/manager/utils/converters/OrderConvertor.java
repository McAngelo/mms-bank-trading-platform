package com.mms.order.manager.utils.converters;

import com.mms.order.manager.dtos.internal.CreateExchangeOrderDto;
import com.mms.order.manager.dtos.requests.CreateOrderDto;
import com.mms.order.manager.enums.OrderType;
import com.mms.order.manager.models.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

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
}
