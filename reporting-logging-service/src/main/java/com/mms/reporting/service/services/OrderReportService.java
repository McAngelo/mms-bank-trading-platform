package com.mms.reporting.service.services;


import com.mms.reporting.service.dtos.*;
import com.mms.reporting.service.enums.SearchFieldDataType;
import com.mms.reporting.service.helper.*;
import com.mms.reporting.service.models.Execution;
import com.mms.reporting.service.models.OrderActivity;
import com.mms.reporting.service.models.OrderReport;
import com.mms.reporting.service.repositories.OrderReportRepository;
import com.mms.reporting.service.utils.ApiResponseUtil;
import com.mms.reporting.service.utils.DtosUtil;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

            return ApiResponseUtil.toCreatedApiResponse(response);
        } catch (DuplicateKeyException ex) {
            List<ErrorDetails> errors = List.of(new ErrorDetails("orderId", "OrderReport with id '" + request.orderId() + "' already exists"));
            return ApiResponseUtil.toBadRequestApiResponse(errors);
        } catch (Exception ex) {
            return ApiResponseUtil.toInternalServerErrorApiResponse(null);
        }
    }

    public IApiResponse<OrderReportResponseDto> getOrderReport(long orderId) {
        try {
            OrderReport orderReport = orderReportRepository.findByOrderId(orderId);

            if (orderReport == null) {
                return ApiResponseUtil.toNotFoundApiResponse();
            }

            OrderReportResponseDto response = DtosUtil.orderReportToOrderReportResponseDto(orderReport);

            return ApiResponseUtil.toOkApiResponse(response);
        } catch (Exception ex) {
            return ApiResponseUtil.toInternalServerErrorApiResponse(null);
        }
    }

    /*
    public IApiResponse<PagedList<OrderReportResponseDto>> getOrderReports(BaseFilter filter) {
        try {
            // Create Pageable instance
            Sort sort = Sort.by(Sort.Direction.ASC/*.fromString(filter.sortOrder())//, "createdAt"); // Adjust sorting field as necessary
            Pageable pageable = (Pageable) PageRequest.of(filter.page() - 1, filter.pageSize(), sort);

            // Query repository with Pageable
            Page<OrderReport> page = orderReportRepository.findByDateBetween(filter.fromDate(), filter.toDate(), pageable);

            // Convert to PagedList and DTOs as needed
            List<OrderReportResponseDto> dtos = page.getContent().stream()
                    .map(DtosUtil::orderReportToOrderReportResponseDto)
                    .collect(Collectors.toList());
            PagedList<OrderReportResponseDto> pagedList = new PagedList<>(dtos, filter.page(), filter.pageSize(), (int) page.getTotalElements());

            return ApiResponseUtil.toOkApiResponse(pagedList);
        } catch (Exception ex) {
            return ApiResponseUtil.toInternalServerErrorApiResponse(null);
        }
    }
    */

    public IApiResponse<PagedList<OrderReportResponseDto>> getOrderReports(BaseFilter filter) {
        try {


            Stream<OrderReport> orderReports = orderReportRepository.findAll().stream();

            if (filter.fromDate() != null) {
                orderReports = orderReports.filter(orderReport -> orderReport.getCreatedAt().isAfter(filter.fromDate()));
            }

            if (filter.toDate() != null) {
                orderReports = orderReports.filter(orderReport -> orderReport.getCreatedAt().isBefore(filter.toDate()));
            }

            // now sort the orderReports by createdAt in the sort order
            OrderReportComparator orderReportComparator = new OrderReportComparator();

            if (filter.sortOrder().equals("desc")) {
                orderReports = orderReports.sorted(orderReportComparator.reversed());
            } else {
                orderReports = orderReports.sorted(orderReportComparator);
            }

            // Convert the stream to a list first to avoid consuming it
            List<OrderReport> filteredSortedReports = orderReports.toList();

            // Use the size of the list for totalCount
            var totalCount = filteredSortedReports.size();

            // Apply pagination on the list
            List<OrderReportResponseDto> dtos = filteredSortedReports.stream()
                    .skip(filter.getOffset())
                    .limit(filter.getLimit())
                    .map(DtosUtil::orderReportToOrderReportResponseDto)
                    .collect(Collectors.toList());

            PagedList<OrderReportResponseDto> pagedList = new PagedList<>(dtos, filter.page(), filter.pageSize(), totalCount);

            return ApiResponseUtil.toOkApiResponse(pagedList);
        } catch (Exception ex) {
            return ApiResponseUtil.toInternalServerErrorApiResponse(null);
        }
    }

    public IApiResponse<OrderReportResponseDto> updateOrderExecutions(long orderId, ExecutionDto request) {
        try {

            OrderReport orderReport = orderReportRepository.findByOrderId(orderId);

            if (orderReport == null) {
                return ApiResponseUtil.toNotFoundApiResponse();
            }

            orderReport.getExecutions().add(new Execution(request.id(), request.orderId(), request.exchangeId(), request.price(), request.quantity()));

            OrderReport updatedOrderReport = orderReportRepository.save(orderReport);

            return ApiResponseUtil.toOkApiResponse(DtosUtil.orderReportToOrderReportResponseDto(updatedOrderReport));
        } catch (Exception ex) {
            return ApiResponseUtil.toInternalServerErrorApiResponse(null);
        }
    }

    public IApiResponse<OrderReportResponseDto> updateOrderOrderActivities(long orderId, OrderActivityDto request) {
        try {

            OrderReport orderReport = orderReportRepository.findByOrderId(orderId);

            if (orderReport == null) {
                return ApiResponseUtil.toNotFoundApiResponse();
            }

            orderReport.getOrderActivities().add(new OrderActivity(request.status(), request.timestamp()));

            OrderReport updatedOrderReport = orderReportRepository.save(orderReport);

            return ApiResponseUtil.toOkApiResponse(DtosUtil.orderReportToOrderReportResponseDto(updatedOrderReport));
        } catch (Exception ex) {
            return ApiResponseUtil.toInternalServerErrorApiResponse(null);
        }
    }

    public IApiResponse<List<OrderReportResponseDto>> orderReportSearch(String field, Object value, SearchFieldDataType type) {
        try {

            List<OrderReport> orderReports = orderReportRepository.findByFields(field, value, type);

            List<OrderReportResponseDto> response = orderReports.stream()
                    .map(DtosUtil::orderReportToOrderReportResponseDto)
                    .collect(Collectors.toList());

            return ApiResponseUtil.toOkApiResponse(response);
        } catch (Exception ex) {
            return ApiResponseUtil.toInternalServerErrorApiResponse(null);
        }
    }

    public IApiResponse<PagedList<OrderReportResponseDto>> orderReportSearch(List<SearchRequest> searchRequests, BaseFilter filter) {
        try {

            var result = orderReportRepository.findByFields(searchRequests, filter.getOffset(), filter.getLimit(), filter.fromDate(), filter.toDate());
            List<OrderReport> orderReports = result.values().stream().flatMap(List::stream).toList();

            List<OrderReportResponseDto> dtos = orderReports.stream()
                    .map(DtosUtil::orderReportToOrderReportResponseDto)
                    .collect(Collectors.toList());

            int totalCount = result.keySet().stream().findFirst().orElse(0);

            PagedList<OrderReportResponseDto> pagedList = new PagedList<>(dtos, filter.page(), filter.pageSize(), totalCount);

            return ApiResponseUtil.toOkApiResponse(pagedList);
        } catch (Exception ex) {
            return ApiResponseUtil.toInternalServerErrorApiResponse(null);
        }
    }
}
