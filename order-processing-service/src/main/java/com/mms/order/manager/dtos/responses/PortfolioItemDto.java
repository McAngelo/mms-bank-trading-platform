package com.mms.order.manager.dtos.responses;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Builder
public class PortfolioItemDto {
    private String productName;
    private Integer quantity;
    private BigDecimal price;
}
