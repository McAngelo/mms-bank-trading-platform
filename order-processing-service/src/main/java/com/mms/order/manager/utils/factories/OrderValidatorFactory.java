package com.mms.order.manager.utils.factories;

import com.mms.order.manager.enums.ExecutionMode;
import com.mms.order.manager.utils.template.OrderValidator;

public interface OrderValidatorFactory {
    OrderValidator getOrderValidator(ExecutionMode executionMode);
}
