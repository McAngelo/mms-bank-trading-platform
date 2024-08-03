package com.mms.order.manager.services.interfaces;

import java.util.List;

public interface RedisService {
    String getValue(String key);
    List<String> getListValues(String key);
}
