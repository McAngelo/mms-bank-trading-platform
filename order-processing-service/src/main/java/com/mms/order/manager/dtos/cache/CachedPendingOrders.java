package com.mms.order.manager.dtos.cache;

import com.mms.order.manager.config.RedisCacheHashKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serial;
import java.io.Serializable;

@RedisHash(RedisCacheHashKey.ORDER)
@AllArgsConstructor
@Builder
public class CachedPendingOrders implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    String exchangeOrderId;
}

