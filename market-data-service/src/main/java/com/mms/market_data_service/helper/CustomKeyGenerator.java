package com.mms.market_data_service.helper;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

public class CustomKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        return null;
    }
}
