package com.mms.order.manager.dtos.responses;

import com.mms.order.manager.enums.OrderSide;
import com.mms.order.manager.enums.OrderStatus;
import com.mms.order.manager.enums.OrderType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record GetOrdersDto(
        long id,
        OrderSide side,
        int quantity,
        BigDecimal price,
        String ticker,
        OrderType orderType,
        String status,
        String dateCreated
) { }
