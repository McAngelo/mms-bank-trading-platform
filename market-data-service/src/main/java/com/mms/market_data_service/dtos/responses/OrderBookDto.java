package com.mms.market_data_service.dtos.responses;


import com.mms.market_data_service.enums.OrderSide;
import com.mms.market_data_service.enums.OrderType;

import java.util.List;


public record OrderBookDto (
        String product,
        int quantity,
        double price,
        OrderSide side,
        OrderType orderType,
        List<ExecutionDto> executions,
        int cumulativeQuantity
) { }
