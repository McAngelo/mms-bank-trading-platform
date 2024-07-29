package com.mms.order.manager.utils.mappers;

import com.mms.order.manager.dtos.requests.CreateOrderDto;
import com.mms.order.manager.dtos.responses.GetOrderDto;
import com.mms.order.manager.enums.OrderType;
import com.mms.order.manager.exceptions.OrderException;
import com.mms.order.manager.models.Order;
import com.mms.order.manager.models.Portfolio;
import com.mms.order.manager.models.Product;
import com.mms.order.manager.models.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = OrderIdsMapper.class)
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order toOrder(CreateOrderDto orderDto);

    @AfterMapping
    default void setPriceForLimitOrder(CreateOrderDto orderDto, @MappingTarget Order order) {
        if (OrderType.LIMIT.equals(orderDto.type())) {
            order.setPrice(orderDto.price());
        } else {
            order.setPrice(null);
        }
    }
}
