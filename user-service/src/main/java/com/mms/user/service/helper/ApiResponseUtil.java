package com.mms.user.service.helper;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ApiResponseUtil {

    public static <T> ApiResponse<T> toOkApiResponse(T data, String message) {
        return toApiResponse(data, message, HttpStatus.OK.value(), null);
    }

    public static <T> ApiResponse<T> toCreatedApiResponse(T data, String message) {
        return toApiResponse(data, message, HttpStatus.CREATED.value(), null);
    }

    public static <T> ApiResponse<T> toAcceptedApiResponse(T data, String message) {
        return toApiResponse(data, message, HttpStatus.ACCEPTED.value(), null);
    }

    public static <T> ApiResponse<T> toNotFoundApiResponse(T data, String message) {
        return toApiResponse(data, message, HttpStatus.NOT_FOUND.value(), null);
    }

    public static <T> ApiResponse<T> toFailedDependencyApiResponse(T data, String message, List<ErrorDetails> errors) {
        return toApiResponse(data, message, HttpStatus.FAILED_DEPENDENCY.value(), errors);
    }

    public static <T> ApiResponse<T> toUnAthorizedApiResponse(String message, List<ErrorDetails> error) {
        return toApiResponse(null, message, HttpStatus.UNAUTHORIZED.value(), error);
    }

    public static <T> ApiResponse<T> toInternalServerErrorApiResponse(String message, List<ErrorDetails> errors) {
        return toApiResponse(null, message, HttpStatus.INTERNAL_SERVER_ERROR.value(), errors);
    }

    public static <T> ApiResponse<T> toBadRequestApiResponse(String message, List<ErrorDetails> errors) {
        return toApiResponse(null, message, HttpStatus.BAD_REQUEST.value(), errors);
    }

    private static <T> ApiResponse<T> toApiResponse(T data, String message, int status, List<ErrorDetails> errors) {
        return new ApiResponse<>(data, message, status, errors);
    }
}