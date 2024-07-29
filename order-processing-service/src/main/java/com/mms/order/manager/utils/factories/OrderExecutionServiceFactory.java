package com.mms.order.manager.utils.factories;

import com.mms.order.manager.enums.ExecutionMode;
import com.mms.order.manager.exceptions.OrderException;
import com.mms.order.manager.services.interfaces.OrderExecutionService;

public interface OrderExecutionServiceFactory {
    OrderExecutionService getOrderExecutionService(ExecutionMode executionMode) throws OrderException;
}
