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

    public static <T> ApiResponse<T> toFailedDependencyApiResponse(T data, String message, List<Error> errors) {
        return toApiResponse(data, message, HttpStatus.FAILED_DEPENDENCY.value(), errors);
    }

    public static <T> ApiResponse<T> toUnAthorizedApiResponse(T data, String message) {
        return toApiResponse(data, message, HttpStatus.UNAUTHORIZED.value(), null);
    }

    public static <T> ApiResponse<T> toInternalServerErrorApiResponse(T data, String message, List<Error> errors) {
        return toApiResponse(data, message, HttpStatus.INTERNAL_SERVER_ERROR.value(), errors);
    }

    public static <T> ApiResponse<T> toBadRequestApiResponse(T data, String message, List<Error> errors) {
        return toApiResponse(data, message, HttpStatus.BAD_REQUEST.value(), errors);
    }

    private static <T> ApiResponse<T> toApiResponse(T data, String message, int status, List<Error> errors) {
        return new ApiResponse<>();
    }
}