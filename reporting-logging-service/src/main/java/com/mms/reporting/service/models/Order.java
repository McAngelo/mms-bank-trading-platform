package com.mms.reporting.service.models;

import com.mms.reporting.service.enums.OrderSide;
import com.mms.reporting.service.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private long id;

    private String productId;

    private String portfolioId;

    private String userId;

    @Enumerated(EnumType.STRING)
    private OrderSide side;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private int quantity;

    private BigDecimal price;
}