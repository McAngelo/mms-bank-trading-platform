package com.mms.order.manager.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExchangeException extends Exception {

    public ExchangeException(String message) {
        super(message);
        log.error(message);
    }

    public ExchangeException(String message, Throwable cause) {
        super(message, cause);
        log.error(message, cause);
    }
}
