package com.mms.order.manager.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PortfolioException extends Exception {

    public PortfolioException(String message) {
        super(message);
        log.error(message);
    }

    public PortfolioException(String message, Throwable cause) {
        super(message, cause);
        log.error(message, cause);
    }
}
