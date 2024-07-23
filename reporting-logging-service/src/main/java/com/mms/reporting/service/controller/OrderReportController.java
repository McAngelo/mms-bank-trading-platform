package com.mms.reporting.service.controller;


import com.mms.reporting.service.dtos.CreateOrderReportDto;
import com.mms.reporting.service.dtos.OrderReportResponseDto;
import com.mms.reporting.service.helper.IApiResponse;
import com.mms.reporting.service.services.OrderReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order-report")
public class OrderReportController {

    private final OrderReportService orderReportService;

    @Autowired
    public OrderReportController(OrderReportService orderReportService) {
        this.orderReportService = orderReportService;
    }

    @PostMapping("/create")
    public IApiResponse<OrderReportResponseDto> createOrderReport(@RequestBody CreateOrderReportDto request) {
        return orderReportService.createOrderReport(request);
    }
}
