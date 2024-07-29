package com.mms.reporting.service.repositories;

import com.mms.reporting.service.dtos.SearchQueryDto;
import com.mms.reporting.service.dtos.SearchRequest;
import com.mms.reporting.service.enums.SearchFieldDataType;
import com.mms.reporting.service.models.OrderReport;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface OrderReportRepositoryCustom {
    List<OrderReport> findByFields(String field, Object value, SearchFieldDataType type);
    Map<Integer, List<OrderReport>> findByFields(List<SearchRequest> searchRequests, int skip, int limit, LocalDateTime fromDate, LocalDateTime toDate);
    Map<Integer, List<OrderReport>> findByFields(SearchQueryDto filter);
}

