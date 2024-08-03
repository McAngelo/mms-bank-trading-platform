package com.mms.order.manager.dtos.responses;

import com.mms.order.manager.dtos.internal.ExecutionDto;
import lombok.Builder;

import java.util.List;

@Builder
public record OrderSplitDto (
     String exchangeName,
     int quantity,
     List<ExecutionDto> executions
) {}
