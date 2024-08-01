package com.mms.order.manager.exceptions;

import com.mms.order.manager.helpers.ApiResponse;
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
                HttpStatus.valueOf(apiResponse.getStatus())
        );
    }

    @ExceptionHandler(OrderException.class)
    protected ResponseEntity<ApiResponse<Object>> handleExchangeException(OrderException ex) {
        var apiResponse = ApiResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return buildResponseEntity(apiResponse);
    }

    @ExceptionHandler(ExchangeException.class)
    protected ResponseEntity<ApiResponse<Object>> handleExchangeException(ExchangeException ex) {
        var apiResponse = ApiResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return buildResponseEntity(apiResponse);
    }

    @ExceptionHandler(MarketDataException.class)
    protected ResponseEntity<ApiResponse<Object>> handleExchangeException(MarketDataException ex) {
        var apiResponse = ApiResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return buildResponseEntity(apiResponse);
    }

    @ExceptionHandler(UserException.class)
    protected ResponseEntity<ApiResponse<Object>> handleExchangeException(UserException ex) {
        var apiResponse = ApiResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return buildResponseEntity(apiResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ApiResponse<Object>> handleExchangeException(IllegalArgumentException ex) {
        var apiResponse = ApiResponse.builder()
                .message(ex.getMessage() + ex)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();

        return buildResponseEntity(apiResponse);
    }


}
