package com.mms.order.manager.services.interfaces;

import com.mms.order.manager.dtos.requests.CreateOrderDto;
import com.mms.order.manager.dtos.responses.GetOrderDto;
import com.mms.order.manager.dtos.responses.GetOrdersDto;
import com.mms.order.manager.exceptions.ExchangeException;
import com.mms.order.manager.exceptions.MarketDataException;
import com.mms.order.manager.exceptions.OrderException;

import java.util.List;

public interface OrderService {
    void createOrder(CreateOrderDto orderDto) throws OrderException, MarketDataException, ExchangeException;
    GetOrderDto getOrder(long orderId) throws OrderException;
    List<GetOrdersDto> getOrders(long portfolioId, int page, int siz);
}
