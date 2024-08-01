package com.mms.order.manager.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WalletException extends Exception {

    public WalletException(String message) {
        super(message);
        log.error(message);
    }

    public WalletException(String message, Throwable cause) {
        super(message, cause);
        log.error(message, cause);
    }
}
