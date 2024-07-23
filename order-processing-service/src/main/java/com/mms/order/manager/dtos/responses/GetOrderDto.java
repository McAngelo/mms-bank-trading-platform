package com.mms.order.manager.dtos.responses;

import com.mms.order.manager.enums.OrderSide;
import com.mms.order.manager.enums.OrderStatus;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class GetOrderDto {
    OrderSide side;
    int quantity;
    BigDecimal price;
    OrderStatus orderStatus;
}
