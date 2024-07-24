package com.mms.reporting.service.utils;

import com.mms.reporting.service.dtos.*;
import com.mms.reporting.service.models.*;

import java.time.LocalDateTime;
import java.util.List;

public class DtosUtil {

    public static OrderReport createOrderReportDtoToOrderReport(CreateOrderReportDto createOrderReportDto) {
        User user = new User(createOrderReportDto.user().id(), createOrderReportDto.user().roleId(), createOrderReportDto.user().email(), createOrderReportDto.user().fullName(), createOrderReportDto.user().dob());

        Order order = Order.builder()
                .id(createOrderReportDto.order().id())
                .productId(createOrderReportDto.order().productId())
                .portfolioId(createOrderReportDto.order().portfolioId())
                .userId(createOrderReportDto.order().userId())
                .side(createOrderReportDto.order().side())
                .status(createOrderReportDto.order().status())
                .quantity(createOrderReportDto.order().quantity())
                .price(createOrderReportDto.order().price())
                .build();

        List<OrderActivity> orderActivities = createOrderReportDto.orderActivities().stream()
                .map(orderActivityDto -> OrderActivity.builder()
                        .status(orderActivityDto.status())
                        .timestamp(orderActivityDto.timestamp())
                        .build())
                .toList();

        List<Execution> executions = createOrderReportDto.executions().stream()
                .map(executionDto -> Execution.builder()
                        .id(executionDto.id())
                        .orderId(executionDto.orderId())
                        .exchangeId(executionDto.exchangeId())
                        .quantity(executionDto.quantity())
                        .price(executionDto.price())
                        .build())
                .toList();

        return OrderReport.builder()
                .orderId(createOrderReportDto.orderId())
                .user(user)
                .order(order)
                .orderActivities(orderActivities)
                .executions(executions)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static OrderReportResponseDto orderReportToOrderReportResponseDto(OrderReport orderReport) {

        UserDto user = new UserDto(orderReport.getUser().getId(), orderReport.getUser().getRoleId(), orderReport.getUser().getEmail(), orderReport.getUser().getFullName(), orderReport.getUser().getDob());

        OrderDto order = new OrderDto(orderReport.getOrder().getId(), orderReport.getOrder().getProductId(), orderReport.getOrder().getPortfolioId(), orderReport.getOrder().getUserId(), orderReport.getOrder().getSide(), orderReport.getOrder().getStatus(), orderReport.getOrder().getQuantity(), orderReport.getOrder().getPrice());

        List<OrderActivityDto> orderActivities = orderReport.getOrderActivities().stream()
                .map(orderActivity -> new OrderActivityDto(orderActivity.getStatus(), orderActivity.getTimestamp()))
                .toList();

        List<ExecutionDto> executions = orderReport.getExecutions().stream()
                .map(execution -> new ExecutionDto(execution.getId(), execution.getOrderId(), execution.getExchangeId(), execution.getPrice(), execution.getQuantity()))
                .toList();

        return new OrderReportResponseDto(
                orderReport.getOrderId(),
                user,
                order,
                orderActivities,
                executions,
                orderReport.getCreatedAt(),
                orderReport.getUpdatedAt()
        );
    }
}
