package com.mms.order.manager.dtos.internal;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CreateExchangeOrderDto (
    String product,
    int quantity,
    BigDecimal price,
    String side,
    String type
) {}
