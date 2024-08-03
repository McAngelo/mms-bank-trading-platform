package com.mms.order.manager.dtos.responses;

public record OrderSplitDto (
     String exchangeName,
     int quantity
) {}
