package com.mms.reporting.service.controller;


import com.mms.reporting.service.dtos.CreateOrderReportDto;
import com.mms.reporting.service.dtos.ExecutionDto;
import com.mms.reporting.service.dtos.OrderActivityDto;
import com.mms.reporting.service.dtos.OrderReportResponseDto;
import com.mms.reporting.service.helper.BaseFilter;
import com.mms.reporting.service.helper.IApiResponse;
import com.mms.reporting.service.helper.PagedList;
import com.mms.reporting.service.services.OrderReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/order-report")
public class OrderReportController {

    private final OrderReportService orderReportService;

    @Autowired
    public OrderReportController(OrderReportService orderReportService) {
        this.orderReportService = orderReportService;
    }

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json", headers = {"X-API-VERSION=1"})
    public IApiResponse<OrderReportResponseDto> createOrderReport(@RequestBody CreateOrderReportDto request) {
        return orderReportService.createOrderReport(request);
    }

    @GetMapping(value = "/{orderId}", produces = "application/json", headers = {"X-API-VERSION=1"})
    public IApiResponse<OrderReportResponseDto> getOrderReportByOrderId(@PathVariable long orderId) {
        return orderReportService.getOrderReport(orderId);
    }

    @GetMapping(value = "/search", produces = "application/json", headers = {"X-API-VERSION=1"})
    public IApiResponse<List<OrderReportResponseDto>> orderReportSearch(@RequestParam String field, @RequestParam String value) {
        return orderReportService.orderReportSearch(field, value);
    }

    @GetMapping(value = "all", produces = "application/json", headers = {"X-API-VERSION=1"})
    public IApiResponse<PagedList<OrderReportResponseDto>> getOrderReports(@RequestParam int page, @RequestParam int pageSize,
                                                                           @RequestParam(required = false) LocalDateTime fromDate,
                                                                           @RequestParam(required = false) LocalDateTime toDate,
                                                                           @RequestParam(required = false) String sortOrder) {
        BaseFilter filter = new BaseFilter(page, pageSize, fromDate, toDate, sortOrder);
        return orderReportService.getOrderReports(filter);
    }

    @PatchMapping(value = "/{orderId}/executions", produces = "application/json", headers = {"X-API-VERSION=1"})
    public IApiResponse<OrderReportResponseDto> updateOrderExecutions(@PathVariable long orderId, @RequestBody ExecutionDto request) {
        return orderReportService.updateOrderExecutions(orderId, request);
    }

    @PatchMapping(value = "/{orderId}/order-activities", produces = "application/json", headers = {"X-API-VERSION=1"})
    public IApiResponse<OrderReportResponseDto> updateOrderOrderActivities(@PathVariable long orderId, @RequestBody OrderActivityDto request) {
        return orderReportService.updateOrderOrderActivities(orderId, request);
    }
}
