package com.mms.reporting.service.services;


import com.mms.reporting.service.dtos.CreateOrderReportDto;
import com.mms.reporting.service.dtos.OrderReportResponseDto;
import com.mms.reporting.service.helper.IApiResponse;
import com.mms.reporting.service.repositories.OrderReportRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderReportService {

    private final OrderReportRepository orderReportRepository;

    public OrderReportService(OrderReportRepository orderReportRepository) {
        this.orderReportRepository = orderReportRepository;
    }

    public IApiResponse<OrderReportResponseDto> createOrderReport(CreateOrderReportDto request) {



        return null;
    }
}
