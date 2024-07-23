package com.mms.order.manager.services.interfaces;

import com.mms.order.manager.dtos.requests.CreateOrderDto;
import com.mms.order.manager.dtos.responses.GetOrderDto;

import java.util.Optional;

public interface OrderService {
    boolean createOrder(CreateOrderDto orderDto);
    Optional<GetOrderDto> getOrder(long orderId);
    boolean cancelOrder(long orderId);
}
