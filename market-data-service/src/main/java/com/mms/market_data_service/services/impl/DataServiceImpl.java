package com.mms.market_data_service.services.impl;

import com.mms.market_data_service.models.StreamOrder;
import com.mms.market_data_service.services.interfaces.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {

//    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void ingestOrder(StreamOrder order) {
//        saveToRedis(order);
    }

//    private void saveToRedis(StreamOrder order) {
//        try {
//            redisTemplate.opsForValue().set(order.getId(), order.toString());
//
//            LocalDateTime now = LocalDateTime.now();
//            LocalDateTime expiryDateTime = now.plusDays(1);
//
//            redisTemplate.expire(order.getId(), Duration.between(now, expiryDateTime));
//        } catch (Exception e) {
//            System.err.println("Cache exception caught:: " + e.getMessage() + e);
//        }
//    }
}
