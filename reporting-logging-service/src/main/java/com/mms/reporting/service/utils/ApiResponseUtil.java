package com.mms.reporting.service.utils;

import com.mms.reporting.service.enums.DefaultHttpMessage;
import com.mms.reporting.service.helper.ApiResponse;
import com.mms.reporting.service.helper.ErrorDetails;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ApiResponseUtil {


    public static <T> ApiResponse<T> toOkApiResponse(T data) {
        return toApiResponse(data, DefaultHttpMessage.SUCCESS.getMessage(), HttpStatus.OK.value(), null);
    }

    public static <T> ApiResponse<T> toCreatedApiResponse(T data) {
        return toApiResponse(data, DefaultHttpMessage.CREATED.getMessage(), HttpStatus.CREATED.value(), null);
    }

    public static <T> ApiResponse<T> toAcceptedApiResponse(T data) {
        return toApiResponse(data, DefaultHttpMessage.ACCEPTED.getMessage(), HttpStatus.ACCEPTED.value(), null);
    }

    public static <T> ApiResponse<T> toNotFoundApiResponse() {
        return toApiResponse(null, DefaultHttpMessage.NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND.value(), null);
    }

    public static <T> ApiResponse<T> toFailedDependencyApiResponse(List<ErrorDetails> errors) {
        return toApiResponse(null, DefaultHttpMessage.FAILED_DEPENDENCY.getMessage(), HttpStatus.FAILED_DEPENDENCY.value(), errors);
    }

    public static <T> ApiResponse<T> toUnAthorizedApiResponse() {
        return toApiResponse(null, DefaultHttpMessage.UNAUTHORIZED.getMessage(), HttpStatus.UNAUTHORIZED.value(), null);
    }

    public static <T> ApiResponse<T> toInternalServerErrorApiResponse(List<ErrorDetails> errors) {
        return toApiResponse(null, DefaultHttpMessage.INTERNAL_SERVER_ERROR.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), errors);
    }

    public static <T> ApiResponse<T> toBadRequestApiResponse(List<ErrorDetails> errors) {
        return toApiResponse(null, DefaultHttpMessage.BAD_REQUEST.getMessage(), HttpStatus.BAD_REQUEST.value(), errors);
    }

    private static <T> ApiResponse<T> toApiResponse(T data, String message, int status, List<ErrorDetails> errors) {
        return new ApiResponse<>(data, message, status, errors);
    }
}
