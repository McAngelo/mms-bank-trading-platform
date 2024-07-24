package com.mms.reporting.service.repositories;

import com.mms.reporting.service.enums.SearchFieldDataType;
import com.mms.reporting.service.models.OrderReport;

import java.util.List;

public interface OrderReportRepositoryCustom {
    List<OrderReport> findByFields(String field, Object value, SearchFieldDataType type);
}
