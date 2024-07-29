package com.mms.order.manager.dtos.requests;

import com.mms.order.manager.enums.ExecutionMode;
import com.mms.order.manager.enums.OrderSide;
import com.mms.order.manager.enums.OrderType;

import java.math.BigDecimal;

public record CreateOrderDto(
    long portfolioId,
    long userId,
    String productSlug,
    int quantity,
    ExecutionMode executionMode,
    String preferredExchangeSlug,
    BigDecimal price,
    OrderSide side,
    OrderType type
) { }
