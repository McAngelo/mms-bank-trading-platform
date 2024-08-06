package com.mms.market_data_service.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record ExecutionDto(
        @JsonProperty("timestamp")
        LocalDateTime dateTime,
        double price,
        int quantity
) { }
