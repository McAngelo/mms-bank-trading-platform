package com.mms.order.manager.services.interfaces;

import com.mms.order.manager.dtos.requests.CreateOrderDto;
import com.mms.order.manager.exceptions.ExchangeException;
import com.mms.order.manager.exceptions.MarketDataException;
import com.mms.order.manager.exceptions.OrderException;

public interface OrderService {
    void createOrder(CreateOrderDto orderDto) throws OrderException, MarketDataException, ExchangeException;
}
