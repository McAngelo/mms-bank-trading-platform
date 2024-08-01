package com.mms.user.service.exceptions;

import com.mms.user.service.helper.ApiResponseUtil;
import com.mms.user.service.helper.ErrorDetails;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;


import static com.mms.user.service.exceptions.BusinessErrorCodes.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<?> handleException(LockedException exp) {

        ArrayList<ErrorDetails> error = new ArrayList<>();
        error.add(new ErrorDetails(String.valueOf(ACCOUNT_LOCKED.getCode()), exp.getMessage()));

        var response = ApiResponseUtil.toUnAthorizedApiResponse(ACCOUNT_LOCKED.getDescription(), error);
        ResponseEntity.BodyBuilder bd = ResponseEntity.status(response.getStatus());
        return bd.body(response);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<?> handleException(DisabledException exp) {
        ArrayList<ErrorDetails> error = new ArrayList<>();
        error.add(new ErrorDetails(String.valueOf(ACCOUNT_DISABLED.getCode()), exp.getMessage()));

        var response = ApiResponseUtil.toUnAthorizedApiResponse(ACCOUNT_DISABLED.getDescription(), error);
        ResponseEntity.BodyBuilder bd = ResponseEntity.status(response.getStatus());
        return bd.body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleException() {
        ArrayList<ErrorDetails> error = new ArrayList<>();
        error.add(new ErrorDetails(String.valueOf(BAD_CREDENTIALS.getCode()), "Login and / or Password is incorrect"));

        var response = ApiResponseUtil.toUnAthorizedApiResponse(BAD_CREDENTIALS.getDescription(), error);
        ResponseEntity.BodyBuilder bd = ResponseEntity.status(response.getStatus());
        return bd.body(response);
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<?> handleException(MessagingException exp) {
        ArrayList<ErrorDetails> error = new ArrayList<>();
        error.add(new ErrorDetails(String.valueOf(INTERNAL_SERVER_ERROR), exp.getMessage()));

        var response = ApiResponseUtil.toInternalServerErrorApiResponse("Internal Server Error", error);
        ResponseEntity.BodyBuilder bd = ResponseEntity.status(response.getStatus());
        return bd.body(response);
    }

    @ExceptionHandler(ActivationTokenException.class)
    public ResponseEntity<?> handleException(ActivationTokenException exp) {
        ArrayList<ErrorDetails> error = new ArrayList<>();
        error.add(new ErrorDetails(String.valueOf(BAD_REQUEST), exp.getMessage()));

        var response = ApiResponseUtil.toBadRequestApiResponse("Bad Request", error);
        ResponseEntity.BodyBuilder bd = ResponseEntity.status(response.getStatus());
        return bd.body(response);
    }

    @ExceptionHandler(OperationNotPermittedException.class)
    public ResponseEntity<?> handleException(OperationNotPermittedException exp) {
        ArrayList<ErrorDetails> error = new ArrayList<>();
        error.add(new ErrorDetails(String.valueOf(BAD_REQUEST), exp.getMessage()));

        var response = ApiResponseUtil.toBadRequestApiResponse("Bad Request", error);
        ResponseEntity.BodyBuilder bd = ResponseEntity.status(response.getStatus());
        return bd.body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp) {
        ArrayList<ErrorDetails> error = new ArrayList<>();
        exp.getBindingResult().getAllErrors()
                .forEach(err -> {
                    error.add(new ErrorDetails(String.valueOf(BAD_REQUEST), err.getDefaultMessage()));
                });
        var response = ApiResponseUtil.toBadRequestApiResponse("Bad Request", error);
        ResponseEntity.BodyBuilder bd = ResponseEntity.status(response.getStatus());
        return bd.body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exp) {
        exp.printStackTrace();
        ArrayList<ErrorDetails> error = new ArrayList<>();
        error.add(new ErrorDetails(String.valueOf(INTERNAL_SERVER_ERROR), exp.getMessage()));

        var response = ApiResponseUtil.toInternalServerErrorApiResponse("Internal Server Error", error);
        ResponseEntity.BodyBuilder bd = ResponseEntity.status(response.getStatus());
        return bd.body(response);
    }
}

