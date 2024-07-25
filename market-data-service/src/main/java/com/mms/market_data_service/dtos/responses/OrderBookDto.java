package com.mms.market_data_service.dtos.responses;

import java.util.List;

public record OrderBookDto (
        String product,
        int quantity,
        double price,
        String side,
        String type,
        List<ExecutionDto> executions,
        int cumulativeQuantity
) { }
