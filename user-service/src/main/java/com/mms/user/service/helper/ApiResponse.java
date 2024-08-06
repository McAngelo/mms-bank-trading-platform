package com.mms.user.service.helper;


import java.util.List;

public class ApiResponse<T> implements IApiResponse<T> {
    private final int status;
    private final T data;
    private final List<ErrorDetails> errors;
    private String message;

    public ApiResponse(T data, String message, int status, List<ErrorDetails> errors) {
        this.message = message;
        this.status = status;
        this.data = data;
        this.errors = errors;
    }

    /*public ApiResponse() {
        this.message = null;
        this.status = 0;
        this.data = null;
        this.errors = null;
    }*/

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
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
    public List<ErrorDetails> getErrors() {
        return errors;
    }
}