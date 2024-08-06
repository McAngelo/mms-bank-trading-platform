package com.mms.order.manager.services.interfaces;

import com.mms.order.manager.exceptions.ExchangeException;
import com.mms.order.manager.models.Order;

import java.io.IOException;

public interface OrderSplittingStrategyService {
    void executeOrder(Order order) throws ExchangeException;
}
