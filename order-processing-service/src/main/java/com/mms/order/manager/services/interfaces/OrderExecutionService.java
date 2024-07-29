package com.mms.order.manager.services.interfaces;

import com.mms.order.manager.exceptions.ExchangeException;
import com.mms.order.manager.models.Order;

public interface OrderExecutionService {

    boolean executeOrder(Order order) throws ExchangeException;
}
