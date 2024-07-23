package com.mms.market_data_service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Builder
@RedisHash
public class Order {
    private String id;
    private String orderType;
    private String product;
    private String side;
    @Indexed
    private UUID orderID;
    private double price;
    private int qty;
    private int cumQty;
    private String cumPrx;
    private String exchange;
    private LocalDateTime timestamp;
}
