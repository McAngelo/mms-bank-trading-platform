package com.mms.market_data_service.dtos.responses;

import java.time.LocalDateTime;

public record ExecutionDto(
        LocalDateTime dateTime,
        double price,
        int quantity
) {
}
