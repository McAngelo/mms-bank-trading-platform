package com.mms.user.service.services;

import com.mms.user.service.dtos.ForgottenPasswordResetDto;
import com.mms.user.service.dtos.RegistrationRequestDto;
import com.mms.user.service.dtos.VerificationDto;
import com.mms.user.service.helper.ErrorDetails;
import com.mms.user.service.dtos.LoginDto;
import com.mms.user.service.helper.ApiResponse;
import com.mms.user.service.helper.ApiResponseUtil;
import com.mms.user.service.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    //@Autowired
    UserRepository userRepository;

    public ApiResponse registration(RegistrationRequestDto request){
        try {
            logger.info("Processing User registration");
            //TODO: process the user registration
            logger.info("registration process response");
            return ApiResponseUtil.toOkApiResponse(request, "User registration Successful");
        }catch(Exception exception){
            logger.error("error while processing registration", exception);
            ArrayList<Error> error = new ArrayList<>();
            error.add(new Error());
            return ApiResponseUtil.toBadRequestApiResponse(null, "Error", error);
        }
    }

    public ApiResponse processLogin(LoginDto loginDto){
        try {
            logger.info("Processing login authentication");
            //TODO: process the password
            logger.info("login process response: ");
            return ApiResponseUtil.toOkApiResponse(loginDto, "Successful Subscription to the first exchange");
        }catch(Exception exception){
            logger.error("error while processing login: {}", exception);
            ArrayList<Error> error = new ArrayList<>();
            error.add(new Error());
            return ApiResponseUtil.toBadRequestApiResponse(null, "Error", error);
        }
    }

    public ApiResponse processVerification(VerificationDto verificationDto){
        try {
            logger.info("Processing login authentication");
            //TODO: process the password
            logger.info("login process response: ");
            return ApiResponseUtil.toOkApiResponse(verificationDto, "Successful Subscription to the first exchange");
        }catch(Exception exception){
            logger.error("error while processing login: {}", exception);
            ArrayList<Error> error = new ArrayList<>();
            error.add(new Error());
            return ApiResponseUtil.toBadRequestApiResponse(null, "Error", error);
        }
    }

    public ApiResponse processPasswordrestToken(String email){
        try {
            logger.info("Processing login authentication");
            //TODO: process the password
            logger.info("login process response: ");
            return ApiResponseUtil.toOkApiResponse(email, "Successful Subscription to the first exchange");
        }catch(Exception exception){
            logger.error("error while processing login: {}", exception);
            ArrayList<Error> error = new ArrayList<>();
            error.add(new Error());
            return ApiResponseUtil.toBadRequestApiResponse(null, "Error", error);
        }
    }

    public ApiResponse processPasswordrest(ForgottenPasswordResetDto forgottenPasswordResetDto){
        try {
            logger.info("Processing login authentication");
            //TODO: process the password
            logger.info("login process response: ");
            return ApiResponseUtil.toOkApiResponse(forgottenPasswordResetDto, "Successful Subscription to the first exchange");
        }catch(Exception exception){
            logger.error("error while processing login: {}", exception);
            ArrayList<Error> error = new ArrayList<>();
            error.add(new Error());
            return ApiResponseUtil.toBadRequestApiResponse(null, "Error", error);
        }
    }
}
