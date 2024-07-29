package com.mms.order.manager.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MarketDataException extends Exception {
    public MarketDataException(String message) {
        super(message);
        log.error(message);
    }

    public MarketDataException(String message, Throwable cause) {
        super(message, cause);
        log.error(message, cause);
    }
}
