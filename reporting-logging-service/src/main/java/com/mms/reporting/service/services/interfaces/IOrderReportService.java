package com.mms.reporting.service.services.interfaces;

import com.mms.reporting.service.dtos.orderreport.*;
import com.mms.reporting.service.enums.SearchFieldDataType;
import com.mms.reporting.service.helper.BaseFilter;
import com.mms.reporting.service.helper.IApiResponse;
import com.mms.reporting.service.helper.PagedList;

import java.util.List;

public interface IOrderReportService {
    IApiResponse<OrderReportResponseDto> createOrderReport(CreateOrderReportDto request);
    IApiResponse<OrderReportResponseDto> getOrderReport(long orderId);
    IApiResponse<PagedList<OrderReportResponseDto>> getOrderReports(BaseFilter filter);
    IApiResponse<OrderReportResponseDto> updateOrderExecutions(long orderId, ExecutionDto request);
    IApiResponse<OrderReportResponseDto> updateOrderOrderActivities(long orderId, OrderActivityDto request);
    IApiResponse<List<OrderReportResponseDto>> orderReportSearch(String field, Object value, SearchFieldDataType type);
    IApiResponse<PagedList<OrderReportResponseDto>> orderReportSearch(List<SearchRequest> searchRequests, BaseFilter filter);
    IApiResponse<PagedList<OrderReportResponseDto>> orderReportSearch(SearchQueryDto filter);
}
