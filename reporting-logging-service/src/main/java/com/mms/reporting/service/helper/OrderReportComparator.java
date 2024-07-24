package com.mms.reporting.service.helper;

import com.mms.reporting.service.models.OrderReport;

import java.time.LocalDateTime;
import java.util.Comparator;

public class OrderReportComparator implements Comparator<OrderReport> {
    @Override
    public int compare(OrderReport o1, OrderReport o2) {

        LocalDateTime createdAt1 = o1.getCreatedAt();
        LocalDateTime createdAt2 = o2.getCreatedAt();
        if (createdAt1 == null && createdAt2 == null) return 0;
        if (createdAt1 == null) return 1; // Consider nulls last
        if (createdAt2 == null) return -1;
        return o1.getCreatedAt().compareTo(o2.getCreatedAt());
    }
}
