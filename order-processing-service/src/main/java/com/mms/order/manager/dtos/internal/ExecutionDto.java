package com.mms.order.manager.dtos.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ExecutionDto (
        @JsonProperty("timestamp")
        LocalDateTime dateTime,
        double price,
        int quantity
) { }
