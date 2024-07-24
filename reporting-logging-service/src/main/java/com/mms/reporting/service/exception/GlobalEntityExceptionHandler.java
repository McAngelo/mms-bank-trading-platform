package com.mms.reporting.service.exception;

import com.mms.reporting.service.helper.ErrorDetails;
import com.mms.reporting.service.helper.IApiResponse;
import com.mms.reporting.service.utils.ApiResponseUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<ErrorDetails> errors = new ArrayList<>();
        ex.getFieldErrors().forEach(e -> errors.add(new ErrorDetails(e.getField(), e.getDefaultMessage())));

        IApiResponse<Object> response = ApiResponseUtil.toBadRequestApiResponse(errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<IApiResponse<Object>> handleAllException(Exception ex, WebRequest request) throws Exception {

        List<ErrorDetails> errors = List.of(new ErrorDetails(ex.getLocalizedMessage().split(":")[1], ex.getLocalizedMessage().split(":")[0]));
        IApiResponse<Object> response = ApiResponseUtil.toInternalServerErrorApiResponse(errors);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}