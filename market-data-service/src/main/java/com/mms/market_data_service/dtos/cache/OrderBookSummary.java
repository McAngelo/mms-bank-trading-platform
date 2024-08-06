package com.mms.market_data_service.dtos.cache;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderBookSummary {
    private String product;
    private long totalBuyQuantity;
    private long totalSellQuantity;
}
