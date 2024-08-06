package com.mms.order.manager.services.interfaces;

import com.mms.order.manager.models.Order;

public interface ExchangeService {
    Object getOrderStatus(String exchangeOrderId);
}
