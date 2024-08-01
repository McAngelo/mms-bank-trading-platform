package com.mms.user.service.services;

import com.mms.user.service.dtos.RegistrationRequestDto;
import com.mms.user.service.dtos.UserResponseDto;
import com.mms.user.service.dtos.VerificationDto;
import com.mms.user.service.helper.*;
import com.mms.user.service.model.User;
import com.mms.user.service.model.mappers.UserMapper;
import com.mms.user.service.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserMapper userMapper;
    private final UserRepository userRepository;


    public IApiResponse<?> processGetAllUsers(int page, int size){
        try {
            logger.info("Get all users");
            //User user = ((User) connectedUser.getPrincipal());
            Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
            var users = userRepository.findAll(pageable);
            logger.info("Users {}", users);
            List<UserResponseDto> usersResponse = users.stream()
                    .map(userMapper::toUserResponse)
                    .toList();
            var pagedRes = new PageResponse<>(
                    usersResponse,
                    users.getNumber(),
                    users.getSize(),
                    users.getTotalElements(),
                    users.getTotalPages(),
                    users.isFirst(),
                    users.isLast());
            logger.info("registration process response");
            return ApiResponseUtil.toOkApiResponse(pagedRes, "Successful");
        }catch(Exception exception){
            logger.error("error while retrieving users", exception);
            ArrayList<ErrorDetails> error = new ArrayList<>();
            error.add(new ErrorDetails(exception.getMessage(), exception.toString()));
            return ApiResponseUtil.toBadRequestApiResponse("Error", error);
        }
    }

    public ApiResponse processGetOneUser(int userId){
        try {
            logger.info("Get one user by Id");
            var user = userRepository.findById(userId)
                    .map(userMapper::toUserResponse);

            logger.info("Get one user by Id {}", user);

            if(user.isEmpty()){
                logger.debug("Could not find user");
                return ApiResponseUtil.toNotFoundApiResponse(null, "User not found");
            }

            return ApiResponseUtil.toOkApiResponse(user, "Successful");
        }catch(Exception exception){
            logger.error("error while user : {}", exception);
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
