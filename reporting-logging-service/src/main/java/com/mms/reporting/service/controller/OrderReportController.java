package com.mms.reporting.service.controller;


import com.mms.reporting.service.dtos.CreateOrderReportDto;
import com.mms.reporting.service.dtos.ExecutionDto;
import com.mms.reporting.service.dtos.OrderActivityDto;
import com.mms.reporting.service.dtos.OrderReportResponseDto;
import com.mms.reporting.service.enums.SearchFieldDataType;
import com.mms.reporting.service.helper.BaseFilter;
import com.mms.reporting.service.helper.IApiResponse;
import com.mms.reporting.service.helper.PagedList;
import com.mms.reporting.service.services.OrderReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/order-report")
@ApiResponses(value = {
        @ApiResponse(responseCode = "424", description = "Failure maybe due to incomplete request payload/params causing internal exceptions",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Server may be down",
                content = @Content)})
public class OrderReportController {

    private final OrderReportService orderReportService;

    @Autowired
    public OrderReportController(OrderReportService orderReportService) {
        this.orderReportService = orderReportService;
    }

    @Operation(summary = "Create an order report", description = "Create an order report", tags = {"Order Report"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created order report",
                    content = {@Content(mediaType = "application/json")}),

            @ApiResponse(responseCode = "400", description = "Invalid request payload",
                    content = @Content)})
    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json", headers = {"X-API-VERSION=1"})
    public IApiResponse<OrderReportResponseDto> createOrderReport(@RequestBody CreateOrderReportDto request) {
        return orderReportService.createOrderReport(request);
    }


    @Operation(summary = "Get an order report", description = "Get an order report by orderId", tags = {"Order Report"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved order reports",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))})})
    @GetMapping(value = "/{orderId}", produces = "application/json", headers = {"X-API-VERSION=1"})
    public IApiResponse<OrderReportResponseDto> getOrderReportByOrderId(@PathVariable long orderId) {
        return orderReportService.getOrderReport(orderId);
    }

    @GetMapping(value = "/search", produces = "application/json", headers = {"X-API-VERSION=1"})
    public IApiResponse<List<OrderReportResponseDto>> orderReportSearch(@RequestParam String field, @RequestParam Object value, @RequestParam SearchFieldDataType type) {
        return orderReportService.orderReportSearch(field, value, type);
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
