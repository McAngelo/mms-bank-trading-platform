package com.mms.user.service.services;

import com.mms.user.service.dtos.RegistrationRequestDto;
import com.mms.user.service.dtos.VerificationDto;
import com.mms.user.service.helper.ApiResponse;
import com.mms.user.service.helper.ApiResponseUtil;
import com.mms.user.service.helper.ErrorDetails;
import com.mms.user.service.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    UserRepository userRepository;


    public ApiResponse processGetAllUsers(){
        try {
            logger.info("Processing User registration");
            //TODO: process the user registration
            logger.info("registration process response");
            return ApiResponseUtil.toOkApiResponse(null, "User registration Successful");
        }catch(Exception exception){
            logger.error("error while processing registration", exception);
            ArrayList<ErrorDetails> error = new ArrayList<>();
            error.add(new ErrorDetails(exception.getMessage(), exception.toString()));
            return ApiResponseUtil.toBadRequestApiResponse("Error", error);
        }
    }

    public ApiResponse processGetOneUser(String userId){
        try {
            logger.info("Processing login authentication");
            //TODO: process the password
            logger.info("login process response: ");
            return ApiResponseUtil.toOkApiResponse(userId, "Successful Subscription to the first exchange");
        }catch(Exception exception){
            logger.error("error while processing login: {}", exception);
            ArrayList<ErrorDetails> error = new ArrayList<>();
            error.add(new ErrorDetails(exception.getMessage(), exception.toString()));
            return ApiResponseUtil.toBadRequestApiResponse("Error", error);
        }
    }

    public ApiResponse processUserSearch(VerificationDto verificationDto){
        try {
            logger.info("Processing login authentication");
            //TODO: process the password
            logger.info("login process response: ");
            return ApiResponseUtil.toOkApiResponse(verificationDto, "Successful Subscription to the first exchange");
        }catch(Exception exception){
            logger.error("error while processing login: {}", exception);
            ArrayList<ErrorDetails> error = new ArrayList<>();
            error.add(new ErrorDetails(exception.getMessage(), exception.toString()));
            return ApiResponseUtil.toBadRequestApiResponse("Error", error);
        }
    }

    public ApiResponse processUserCreation(RegistrationRequestDto registrationDto){
        try {
            logger.info("Processing login authentication");
            //TODO: process the password
            logger.info("login process response: ");
            return ApiResponseUtil.toOkApiResponse(registrationDto, "Successful Subscription to the first exchange");
        }catch(Exception exception){
            logger.error("error while processing login: {}", exception);
            ArrayList<ErrorDetails> error = new ArrayList<>();
            error.add(new ErrorDetails(exception.getMessage(), exception.toString()));
            return ApiResponseUtil.toBadRequestApiResponse("Error", error);
        }
    }

    public ApiResponse processUpdateUser(String id, RegistrationRequestDto registrationDto){
        try {
            logger.info("Processing login authentication");
            //TODO: process the password
            logger.info("login process response: ");
            return ApiResponseUtil.toOkApiResponse(registrationDto, "Successful Subscription to the first exchange");
        }catch(Exception exception){
            logger.error("error while processing login: {}", exception);
            ArrayList<ErrorDetails> error = new ArrayList<>();
            error.add(new ErrorDetails(exception.getMessage(), exception.toString()));
            return ApiResponseUtil.toBadRequestApiResponse("Error", error);
        }
    }

    public ApiResponse processAccountStatusChange(String id, String status){
        try {
            logger.info("Processing login authentication");
            //TODO: process the password
            logger.info("login process response: ");
            return ApiResponseUtil.toOkApiResponse(status, "Successful Subscription to the first exchange");
        }catch(Exception exception){
            logger.error("error while processing login: {}", exception);
            ArrayList<ErrorDetails> error = new ArrayList<>();
            error.add(new ErrorDetails(exception.getMessage(), exception.toString()));
            return ApiResponseUtil.toBadRequestApiResponse("Error", error);
        }
    }

    public ApiResponse processDeleteUser(String UserId){
        try {
            logger.info("Processing login authentication");
            //TODO: process the password
            logger.info("login process response: ");
            return ApiResponseUtil.toOkApiResponse(UserId, "Successful Subscription to the first exchange");
        }catch(Exception exception){
            logger.error("error while processing login: {}", exception);
            ArrayList<ErrorDetails> error = new ArrayList<>();
            error.add(new ErrorDetails(exception.getMessage(), exception.toString()));
            return ApiResponseUtil.toBadRequestApiResponse("Error", error);
        }
    }
}
