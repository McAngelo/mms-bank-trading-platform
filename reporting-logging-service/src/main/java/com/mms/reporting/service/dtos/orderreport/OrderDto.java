package com.mms.reporting.service.dtos.orderreport;

import com.mms.reporting.service.enums.OrderSide;
import com.mms.reporting.service.enums.OrderStatus;

import java.math.BigDecimal;

public record OrderDto(long id, String productId, String portfolioId, String userId, OrderSide side, OrderStatus status,
                       int quantity, BigDecimal price) {
}
