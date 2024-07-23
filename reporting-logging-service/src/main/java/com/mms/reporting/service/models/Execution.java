package com.mms.reporting.service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Execution {
    private long id;

    private String orderId;

    private String exchangeId;

    private BigDecimal price;
    private int quantity;
}
