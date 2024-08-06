package com.mms.order.manager.dtos.internal;

import java.util.List;

public record UpdatedOrderDto (
    String product,
    int quantity,
    double price,
    String side,
    String type,
    List<ExecutionDto> executions,
    int cumulativeQuantity
) { }
