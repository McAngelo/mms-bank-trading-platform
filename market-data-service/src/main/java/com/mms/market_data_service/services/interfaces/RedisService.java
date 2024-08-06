package com.mms.market_data_service.services.interfaces;

import java.time.Duration;

public interface RedisService {
    void deleteKey(String key);

    void setTTL(String key, Duration duration);

    void addToList(String key, String value);

    void addToHash(String key, String hashKey, String value);
}
