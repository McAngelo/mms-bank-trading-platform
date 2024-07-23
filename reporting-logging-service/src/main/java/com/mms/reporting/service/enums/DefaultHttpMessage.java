package com.mms.reporting.service.enums;

public enum DefaultHttpMessage {
    SUCCESS("Success"),
    CREATED("Created"),
    ACCEPTED("Accepted"),
    ERROR("Error"),
    NOT_FOUND("Not Found"),
    BAD_REQUEST("Bad Request"),
    UNAUTHORIZED("Unauthorized"),
    FORBIDDEN("Forbidden"),
    FAILED_DEPENDENCY("Failed Dependency"),
    INTERNAL_SERVER_ERROR("Oops! Something went wrong, please try again later");

    private final String message;

    DefaultHttpMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}