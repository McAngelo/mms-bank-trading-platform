/*
package com.mms.order.manager.helpers.converters;

import com.mms.order.manager.dtos.requests.CreateOrderDto;
import com.mms.order.manager.dtos.responses.GetOrderDto;
import com.mms.order.manager.models.Order;
import com.mms.order.manager.models.Product;
import com.mms.order.manager.models.User;
import org.springframework.core.convert.converter.Converter;

public class OrderConverter implements Converter<CreateOrderDto, Order> {
    @Override
    public Order convert(CreateOrderDto orderDto) {
        return Order.builder()
                .user(User.builder().id(orderDto.getUserId()).build())
                .product(Product.builder().id(orderDto.getProductId()).build())
                .side(orderDto.getSide())
                .price(orderDto.getPrice())
                .type(orderDto.getType())
                .quantity(orderDto.getQuantity())
                .portfolio(orderDto.getPortfolio())
                .build();
    }

    public GetOrderDto convert(Order order) {
        return GetOrderDto.builder()
                .side(order.getSide())
                .price(order.getPrice())
                .quantity(order.getQuantity())
                .build();
    }


}
*/
