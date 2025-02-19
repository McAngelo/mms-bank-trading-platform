package com.mms.market_data_service.exceptions;

import com.mms.market_data_service.helper.ApiResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<ApiResponse<Object>> buildResponseEntity(ApiResponse<Object> apiResponse) {
        return new ResponseEntity<>(
                apiResponse,
                HttpStatus.valueOf(apiResponse.status())
        );
    }

    @ExceptionHandler(ExchangeException.class)
    protected ResponseEntity<ApiResponse<Object>> handleExchangeException(ExchangeException ex) {
        var apiResponse = ApiResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();

        return buildResponseEntity(apiResponse);
    }
}
