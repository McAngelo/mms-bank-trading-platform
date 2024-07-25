package com.mms.order.manager.dtos.requests;

import com.mms.order.manager.enums.OrderSide;
import com.mms.order.manager.enums.OrderStatus;
import com.mms.order.manager.enums.OrderType;
import com.mms.order.manager.models.Portfolio;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

public record CreateOrderDto(
    String product,
    int quantity,
    BigDecimal price,
    OrderSide side,
    OrderType type
) { }
