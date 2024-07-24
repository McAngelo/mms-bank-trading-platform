package com.mms.user.service.helper;

import java.util.List;

public class ApiResponse<T> implements IApiResponse<T> {
    private final String message;
    private final int status;
    private final T data;
    private final List<Error> errors;

    public ApiResponse(String message, int status, T data, List<Error> errors) {
        this.message = message;
        this.status = status;
        this.data = data;
        this.errors = errors;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public List<Error> getErrors() {
        return errors;
    }
}