package com.mms.order.manager.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderException extends Exception {

    public OrderException(String message) {
        super(message);
        log.warn(message);
    }

    public OrderException(String message, Throwable cause) {
        super(message, cause);
        log.warn(message, cause);
    }
}
