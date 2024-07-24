package com.mms.user.service.services;

import com.mms.user.service.dtos.*;
import com.mms.user.service.helper.ApiResponse;
import com.mms.user.service.helper.ApiResponseUtil;
import com.mms.user.service.helper.Error;
import com.mms.user.service.repositories.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;

@Service
public class RoleService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    RoleRepository roleRepository;


    public ApiResponse processGetAllRoles(){
        try {
            logger.info("Processing User registration");
            //TODO: process the user registration
            logger.info("registration process response");
            return ApiResponseUtil.toOkApiResponse(null, "User registration Successful");
        }catch(Exception exception){
            logger.error("error while processing registration", exception);
            ArrayList<Error> error = new ArrayList<>();
            error.add(new Error(exception.toString(), exception.getMessage()));
            return ApiResponseUtil.toBadRequestApiResponse(null, "Error", error);
        }
    }

    public ApiResponse processGetOneRole(String id){
        try {
            logger.info("Processing login authentication");
            //TODO: process the password
            logger.info("login process response: ");
            return ApiResponseUtil.toOkApiResponse(null, "Successful Subscription to the first exchange");
        }catch(Exception exception){
            logger.error("error while processing login: {}", exception);
            ArrayList<Error> error = new ArrayList<>();
            error.add(new Error(exception.toString(), exception.getMessage()));
            return ApiResponseUtil.toBadRequestApiResponse(null, "Error", error);
        }
    }

    public ApiResponse processRoleCreation(RoleDto roleDto){
        try {
            logger.info("Processing login authentication");
            //TODO: process the password
            logger.info("login process response: ");
            return ApiResponseUtil.toOkApiResponse(roleDto, "Successful Subscription to the first exchange");
        }catch(Exception exception){
            logger.error("error while processing login: {}", exception);
            ArrayList<Error> error = new ArrayList<>();
            error.add(new Error(exception.toString(), exception.getMessage()));
            return ApiResponseUtil.toBadRequestApiResponse(null, "Error", error);
        }
    }

    public ApiResponse processUpdateRole(String id, RoleDto roleDto){
        try {
            logger.info("Processing login authentication");
            //TODO: process the password
            logger.info("login process response: ");
            return ApiResponseUtil.toOkApiResponse(roleDto, "Successful Subscription to the first exchange");
        }catch(Exception exception){
            logger.error("error while processing login: {}", exception);
            ArrayList<Error> error = new ArrayList<>();
            error.add(new Error(exception.toString(), exception.getMessage()));
            return ApiResponseUtil.toBadRequestApiResponse(null, "Error", error);
        }
    }

    public ApiResponse processDeleteRole(String id){
        try {
            logger.info("Processing login authentication");
            //TODO: process the password
            logger.info("login process response: ");
            return ApiResponseUtil.toOkApiResponse(null, "Successful Subscription to the first exchange");
        }catch(Exception exception){
            logger.error("error while processing login: {}", exception);
            ArrayList<Error> error = new ArrayList<>();
            error.add(new Error(exception.toString(), exception.getMessage()));
            return ApiResponseUtil.toBadRequestApiResponse(null, "Error", error);
        }
    }
}
