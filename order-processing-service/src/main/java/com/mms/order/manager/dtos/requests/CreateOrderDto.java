package com.mms.order.manager.dtos.requests;

import com.mms.order.manager.enums.ExecutionMode;
import com.mms.order.manager.enums.OrderSide;
import com.mms.order.manager.enums.OrderType;
import lombok.Builder;
import lombok.ToString;

import java.math.BigDecimal;

@Builder
public record CreateOrderDto (
    long portfolioId,
    long userId,
    String ticker,
    int quantity,
    ExecutionMode executionMode,
    String preferredExchangeSlug,
    BigDecimal price,
    OrderSide side,
    OrderType type
) { }
