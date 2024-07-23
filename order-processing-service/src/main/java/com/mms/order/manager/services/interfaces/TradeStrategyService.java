package com.mms.order.manager.services.interfaces;

import com.mms.order.manager.models.Order;

public interface TradeStrategyService {
    boolean executeOrder(Order order);
}
