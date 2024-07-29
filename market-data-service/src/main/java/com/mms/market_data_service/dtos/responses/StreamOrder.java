package com.mms.market_data_service.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash
public class StreamOrder {
    private String id;
    private String product;
    private int quantity;
    private String side;
    private String type;
    private BigDecimal price;
    private List<Object> executions;
    private int cumulativeQuantity;
    private LocalDateTime dateTime;
    private String topicName;
}
