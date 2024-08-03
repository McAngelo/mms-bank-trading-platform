package com.mms.order.manager.dtos.internal;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record ExecutionDto (
        @JsonProperty("timestamp")
        LocalDateTime dateTime,
        double price,
        java.math.BigDecimal quantity
) { }
