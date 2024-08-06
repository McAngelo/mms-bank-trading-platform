package com.mms.market_data_service.helper;

import lombok.Builder;

import java.util.List;

//@Getter
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