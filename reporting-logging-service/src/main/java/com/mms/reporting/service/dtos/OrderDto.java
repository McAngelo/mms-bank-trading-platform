package com.mms.reporting.service.dtos;

import java.math.BigDecimal;

public record OrderDto(long id, String productId, String portfolioId, String userId, String side, String status, int quantity, BigDecimal price) {
}
