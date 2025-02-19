package com.mms.reporting.service.exception;

import com.mms.reporting.service.helper.ApiResponse;
import com.mms.reporting.service.utils.ApiResponseUtil;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(ConversionFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse<Object>> handleConversion(RuntimeException ex) {
        System.out.println(ex.getLocalizedMessage());
        System.out.println(ex.getMessage());
        System.out.println(ex);
        return new ResponseEntity<>(ApiResponseUtil.toBadRequestApiResponse(null), HttpStatus.BAD_REQUEST);
    }
}