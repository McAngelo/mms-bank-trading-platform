package com.mms.reporting.service.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record OrderReportResponseDto
        (long orderId, UserDto user, OrderDto order,
         List<OrderActivityDto> orderActivities, List<ExecutionDto> executions,
         LocalDateTime createdAt, LocalDateTime updatedAt) {

    public OrderReportResponseDto {
    }
}
