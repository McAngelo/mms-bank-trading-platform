package com.mms.reporting.service.controller;


import com.mms.reporting.service.dtos.*;
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
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/order-report")
@ApiResponses(value = {@ApiResponse(responseCode = "424", description = "Failure maybe due to incomplete request payload/params causing internal exceptions", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = com.mms.reporting.service.helper.ApiResponse.class))}), @ApiResponse(responseCode = "500", description = "Server may be down", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = com.mms.reporting.service.helper.ApiResponse.class))})})
public class OrderReportController {

    private final OrderReportService orderReportService;

    @Autowired
    public OrderReportController(OrderReportService orderReportService) {
        this.orderReportService = orderReportService;
    }

    @Operation(summary = "Create an order report", description = "Create an order report", tags = {"Order Report"})
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Successfully created order report", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = com.mms.reporting.service.helper.ApiResponse.class, type = "object", anyOf = {OrderReportResponseDto.class}))}),

            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = com.mms.reporting.service.helper.ApiResponse.class))})})
    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json", headers = {"X-API-VERSION=1"})
    public ResponseEntity<IApiResponse<OrderReportResponseDto>> createOrderReport(@Valid @RequestBody CreateOrderReportDto request) {

        var result = orderReportService.createOrderReport(request);
        ResponseEntity.BodyBuilder bd = ResponseEntity.status(result.getStatus());
        return bd.body(result);
    }


    @Operation(summary = "Get an order report", description = "Get an order report by orderId", tags = {"Order Report"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved order report by the orderId provided", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = com.mms.reporting.service.helper.ApiResponse.class))}), @ApiResponse(responseCode = "404", description = "Couldn't find order report by the orderId provided", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = com.mms.reporting.service.helper.ApiResponse.class))})})
    @GetMapping(value = "/{orderId}", produces = "application/json", headers = {"X-API-VERSION=1"})
    public ResponseEntity<IApiResponse<OrderReportResponseDto>> getOrderReportByOrderId(@PathVariable long orderId) {

        var result = orderReportService.getOrderReport(orderId);
        ResponseEntity.BodyBuilder bd = ResponseEntity.status(result.getStatus());
        return bd.body(result);
    }

    @Operation(summary = "Search for order report(s)", description = "Get an order report(s) by specified fields and values", tags = {"Order Report"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved order report by the field and value provided", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = com.mms.reporting.service.helper.ApiResponse.class))})})
    @GetMapping(value = "/search", produces = "application/json", headers = {"X-API-VERSION=1"})
    public ResponseEntity<IApiResponse<List<OrderReportResponseDto>>> orderReportSearch(@RequestParam String field, @RequestParam Object value, @RequestParam SearchFieldDataType type) {

        var result = orderReportService.orderReportSearch(field, value, type);
        ResponseEntity.BodyBuilder bd = ResponseEntity.status(result.getStatus());
        return bd.body(result);
    }

    @Operation(summary = "Search for order report(s)", description = "Get an order report(s) by specified fields and values", tags = {"Order Report"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved order report by the field and value provided", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = com.mms.reporting.service.helper.ApiResponse.class))})})
    @PostMapping(value = "/query", produces = "application/json", headers = {"X-API-VERSION=1"})
    public ResponseEntity<IApiResponse<PagedList<OrderReportResponseDto>>> orderReportSearch_v2(@RequestParam int page, @RequestParam int pageSize, @RequestParam(required = false) LocalDateTime fromDate, @RequestParam(required = false) LocalDateTime toDate, @RequestBody List<SearchRequest> searchRequests) {

        BaseFilter filter = new BaseFilter(page, pageSize, fromDate, toDate, null);
        var result = orderReportService.orderReportSearch(searchRequests, filter);
        ResponseEntity.BodyBuilder bd = ResponseEntity.status(result.getStatus());
        return bd.body(result);
    }

    @Operation(summary = "Search for order report(s)", description = "Get an order report(s) by specified fields and values", tags = {"Order Report"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved order report by the field and value provided", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = com.mms.reporting.service.helper.ApiResponse.class))})})
    @GetMapping(value = "search/query", produces = "application/json", headers = {"X-API-VERSION=1"})
    public ResponseEntity<IApiResponse<PagedList<OrderReportResponseDto>>> orderReportSearch_v3(@ParameterObject SearchQueryDto filter) {
        var result = orderReportService.orderReportSearch(filter);
        ResponseEntity.BodyBuilder bd = ResponseEntity.status(result.getStatus());
        return bd.body(result);
    }


    @Operation(summary = "Search for order reports", description = "Get paginated list of order reports", tags = {"Order Report"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieves paginated list of order reports", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = com.mms.reporting.service.helper.ApiResponse.class))})})
    @GetMapping(value = "all", produces = "application/json", headers = {"X-API-VERSION=1"})
    public ResponseEntity<IApiResponse<PagedList<OrderReportResponseDto>>> getOrderReports(@RequestParam int page, @RequestParam int pageSize, @RequestParam(required = false) LocalDateTime fromDate, @RequestParam(required = false) LocalDateTime toDate, @RequestParam(required = false) String sortOrder) {
        BaseFilter filter = new BaseFilter(page, pageSize, fromDate, toDate, sortOrder);
        var result = orderReportService.getOrderReports(filter);
        ResponseEntity.BodyBuilder bd = ResponseEntity.status(result.getStatus());
        return bd.body(result);
    }

    @Operation(summary = "Add an order report execution", description = "Adds execution to list of order report executions", tags = {"Order Report"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully added execution order report's executions list", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = com.mms.reporting.service.helper.ApiResponse.class))}), @ApiResponse(responseCode = "404", description = "Couldn't find order report by the orderId provided", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = com.mms.reporting.service.helper.ApiResponse.class))})})
    @PatchMapping(value = "/{orderId}/executions", produces = "application/json", headers = {"X-API-VERSION=1"})
    public ResponseEntity<IApiResponse<OrderReportResponseDto>> updateOrderExecutions(@PathVariable long orderId, @Valid @RequestBody ExecutionDto request) {

        var result = orderReportService.updateOrderExecutions(orderId, request);
        ResponseEntity.BodyBuilder bd = ResponseEntity.status(result.getStatus());
        return bd.body(result);
    }

    @Operation(summary = "Add an order report order-activity", description = "Adds order-activity to list of order report order-activities", tags = {"Order Report"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully added execution order report's order-activities list", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = com.mms.reporting.service.helper.ApiResponse.class))}), @ApiResponse(responseCode = "404", description = "Couldn't find order report by the orderId provided", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = com.mms.reporting.service.helper.ApiResponse.class))})})
    @PatchMapping(value = "/{orderId}/order-activities", produces = "application/json", headers = {"X-API-VERSION=1"})
    public ResponseEntity<IApiResponse<OrderReportResponseDto>> updateOrderOrderActivities(@PathVariable long orderId, @Valid @RequestBody OrderActivityDto request) {

        var result = orderReportService.updateOrderOrderActivities(orderId, request);
        ResponseEntity.BodyBuilder bd = ResponseEntity.status(result.getStatus());
        return bd.body(result);
    }

}
