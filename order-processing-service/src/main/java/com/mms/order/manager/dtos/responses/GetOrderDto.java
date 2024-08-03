package com.mms.order.manager.dtos.responses;

import com.mms.order.manager.dtos.internal.ExecutionDto;
import com.mms.order.manager.enums.OrderSide;
import com.mms.order.manager.enums.OrderStatus;
import com.mms.order.manager.enums.OrderType;
import com.mms.order.manager.models.Execution;
import com.mms.order.manager.models.OrderSplit;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record GetOrderDto (
    OrderSide side,
    int quantity,
    BigDecimal price,
    OrderStatus orderStatus,
    String ticker,
    OrderType orderType,
    List<OrderSplitDto> orderSplits,
    List<ExecutionDto> executions
) {}
