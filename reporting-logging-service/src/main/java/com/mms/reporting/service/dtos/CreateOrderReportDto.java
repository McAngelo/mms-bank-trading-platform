package com.mms.reporting.service.dtos;

import jakarta.validation.constraints.Min;

import java.util.List;

public record CreateOrderReportDto(@Min(value = 0, message = "Some here") long orderId, UserDto user, OrderDto order, List<OrderActivityDto> orderActivities,
                                   List<ExecutionDto> executions) {

    public CreateOrderReportDto(UserDto user, OrderDto order, List<OrderActivityDto> orderActivities, List<ExecutionDto> executions) {
        this(order.id(), user, order, orderActivities, executions);
    }

    public CreateOrderReportDto(UserDto user, OrderDto order) {
        this(order.id(), user, order, null, null);
    }
}
