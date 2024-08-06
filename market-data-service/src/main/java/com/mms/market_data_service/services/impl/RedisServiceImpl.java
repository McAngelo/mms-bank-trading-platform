package com.mms.market_data_service.services.impl;

import com.mms.market_data_service.services.interfaces.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;


@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void setTTL(String key, Duration duration) {
        redisTemplate.expire(key, duration);
    }

    @Override
    public void addToList(String key, String value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    public void addToHash(String key, String hashKey, String value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }
}
