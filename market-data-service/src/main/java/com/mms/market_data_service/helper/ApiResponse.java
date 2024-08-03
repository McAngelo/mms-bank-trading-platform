package com.mms.market_data_service.helper;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
public record ApiResponse<T>(
        String message,
        int status, T data,
        List<Error> errors
) implements IApiResponse<T> { }


interface IApiResponse<T> {
    String message();
    int status();
    T data();
    List<Error> errors();
}