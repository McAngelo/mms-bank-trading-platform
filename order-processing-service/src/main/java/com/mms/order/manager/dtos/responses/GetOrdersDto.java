package com.mms.order.manager.dtos.responses;

import com.mms.order.manager.enums.OrderSide;
import com.mms.order.manager.enums.OrderStatus;
import com.mms.order.manager.enums.OrderType;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record GetOrdersDto(
        OrderSide side,
        int quantity,
        BigDecimal price,
        OrderStatus orderStatus,
        String ticker,
        OrderType orderType
) { }
