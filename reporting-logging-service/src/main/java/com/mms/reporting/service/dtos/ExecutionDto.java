package com.mms.reporting.service.dtos;

import java.math.BigDecimal;

public record ExecutionDto(long id, String orderId, String exchangeId, BigDecimal price, int quantity) {
}
