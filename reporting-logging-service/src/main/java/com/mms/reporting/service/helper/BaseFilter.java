package com.mms.reporting.service.helper;

import jakarta.validation.constraints.Null;

import java.time.LocalDateTime;

public record BaseFilter(int page, int pageSize, @Null LocalDateTime fromDate, @Null LocalDateTime toDate,
                         @Null String sortOrder) {
    public BaseFilter {
        if (page < 1) {
            page = 1;
        }

        if (pageSize < 0) {
            pageSize = 10;
        }

        if (sortOrder == null) {
            sortOrder = "asc";
        }
    }

    public BaseFilter() {
        this(1, 10, null, null, null);
    }

    public BaseFilter(int page, int pageSize) {
        this(page, pageSize, null, null, null);
    }

    public int getOffset() {
        return (page - 1) * pageSize;
    }

    public int getLimit() {
        return pageSize;
    }
}
