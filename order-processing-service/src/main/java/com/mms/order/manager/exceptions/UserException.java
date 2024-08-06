package com.mms.order.manager.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserException extends Exception {
    public UserException(String message) {
        super(message);
        log.error(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
        log.error(message, cause);
    }
}
