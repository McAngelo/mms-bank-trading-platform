package com.mms.reporting.service.utils;

import com.mms.reporting.service.dtos.CreateOrderReportDto;
import com.mms.reporting.service.models.OrderReport;
import com.mms.reporting.service.models.User;

public class DtosUtil {

    public static OrderReport createOrderReportDtoToOrderReport(CreateOrderReportDto createOrderReportDto) {
        User user = new User(createOrderReportDto.user().id(), createOrderReportDto.user().roleId(), createOrderReportDto.user().email(), createOrderReportDto.user().fullName(), createOrderReportDto.user().dob());

//        Order order = new Order(createOrderReportDto.order().id(), createOrderReportDto.order().symbol(), createOrderReportDto.order().orderType(), createOrderReportDto.order().orderSide(), createOrderReportDto.order().quantity(), createOrderReportDto.order().price(), createOrderReportDto.order().status());

        return OrderReport.builder()
                .orderId(createOrderReportDto.orderId())
                .user(user)
//                .orderSide(createOrderReportDto.getOrderSide())
//                .orderType(createOrderReportDto.getOrderType())
//                .quantity(createOrderReportDto.getQuantity())
//                .price(createOrderReportDto.getPrice())
                .build();
    }
}
