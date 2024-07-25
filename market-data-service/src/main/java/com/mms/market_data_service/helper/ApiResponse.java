package com.mms.market_data_service.helper;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
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
}


interface IApiResponse<T> {
    String getMessage();
    int getStatus();
    T getData();
    List<Error> getErrors();
}