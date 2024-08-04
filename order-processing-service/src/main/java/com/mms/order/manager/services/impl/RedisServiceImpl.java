package com.mms.order.manager.services.impl;

import com.mms.order.manager.services.interfaces.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public List<String> getListValues(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    @Override
    public void addToSet(String key, String value) {
        redisTemplate.opsForSet().add(key, value);
    }

    @Override
    public void removeFromSet(String key, String value) {
        redisTemplate.opsForSet().remove(key, value);
    }

    public void setValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setValue(String key, String value, Duration duration) {
        redisTemplate.opsForValue().set(key, value);

        redisTemplate.expire(key, duration);
    }
}
