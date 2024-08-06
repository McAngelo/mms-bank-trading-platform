package com.mms.order.manager.services.interfaces;

import com.fasterxml.jackson.core.JsonParser;

import java.util.List;

public interface RedisService {
    String getValue(String key);

    List<String> getListValues(String key);

    void removeFromSet(String key, String value);

    void addToSet(String key, String value);

    Object getFromHash(String key, String hashKey);
}
