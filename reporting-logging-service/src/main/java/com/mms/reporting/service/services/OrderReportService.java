package com.mms.reporting.service.services;


import com.mms.reporting.service.dtos.CreateOrderReportDto;
import com.mms.reporting.service.dtos.OrderReportResponseDto;
import com.mms.reporting.service.helper.ErrorDetails;
import com.mms.reporting.service.helper.IApiResponse;
import com.mms.reporting.service.models.OrderReport;
import com.mms.reporting.service.repositories.OrderReportRepository;
import com.mms.reporting.service.utils.ApiResponseUtil;
import com.mms.reporting.service.utils.DtosUtil;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderReportService {

    private final OrderReportRepository orderReportRepository;

    public OrderReportService(OrderReportRepository orderReportRepository) {
        this.orderReportRepository = orderReportRepository;
    }

    public IApiResponse<OrderReportResponseDto> createOrderReport(CreateOrderReportDto request) {
        try {
            OrderReport orderReport = DtosUtil.createOrderReportDtoToOrderReport(request);
            OrderReport savedOrderReport = orderReportRepository.save(orderReport);

            if (savedOrderReport == null) {
                return ApiResponseUtil.toFailedDependencyApiResponse(null);
            }

            OrderReportResponseDto response = DtosUtil.orderReportToOrderReportResponseDto(savedOrderReport);

            return ApiResponseUtil.toOkApiResponse(response);
        } catch (DuplicateKeyException ex) {
            List<ErrorDetails> errors = List.of(new ErrorDetails("orderId", "OrderReport with id '" + request.orderId() + "' already exists"));
            return ApiResponseUtil.toBadRequestApiResponse(errors);
        } catch (Exception ex) {
            return ApiResponseUtil.toInternalServerErrorApiResponse(null);
        }
    }
}
