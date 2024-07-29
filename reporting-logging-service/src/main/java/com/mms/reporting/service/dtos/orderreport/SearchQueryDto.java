package com.mms.reporting.service.dtos.orderreport;

import com.mongodb.lang.Nullable;

import java.math.BigDecimal;

public record SearchQueryDto(int page, int pageSize, String portfolioId, String stock, String orderType, String tradeType, String status,
                             @Nullable int quantity, @Nullable BigDecimal price, String fromDate, String toDate) {

    public SearchQueryDto {
        if (page < 1) {
            page = 1;
        }

        if (pageSize < 0) {
            pageSize = 10;
        }
    }

    public int getOffset() {
        return (page - 1) * pageSize;
    }

    public int getLimit() {
        return pageSize;
    }
}
