package com.mms.user.service.services;


import com.mms.user.service.dtos.*;
import com.mms.user.service.helper.*;
import com.mms.user.service.model.User;
import com.mms.user.service.model.mappers.UserMapper;
import com.mms.user.service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public IApiResponse<?> processGetOneUser(int userId){
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

    /*public IApiResponse<?> processUserSearch(int page, int size, UserSearchDto userSearchDto){
        try {
            logger.info("Search all users");
            Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
            var users = userRepository.findAllUsers(pageable, userSearchDto.getFullName(), userSearchDto.getEmail(), userSearchDto.getRole(), userSearchDto.isAccountLocked(), userSearchDto.isEnabled());
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
    }*/


    public IApiResponse<?> processUserCreation(UserRequestDto registrationDto){
        try {
            logger.info("Creating a user");
            User user = userMapper.toUserRequest(registrationDto);
            var response = userRepository.save(user).getId();
            return ApiResponseUtil.toOkApiResponse(response, "User created successfully");
        }catch(Exception exception){
            logger.error("error while processing user creation: {}", exception);
            ArrayList<ErrorDetails> error = new ArrayList<>();
            error.add(new ErrorDetails(exception.getMessage(), exception.toString()));
            return ApiResponseUtil.toBadRequestApiResponse("Error", error);
        }
    }

    public IApiResponse<?> processUpdateUser(int userId, UserRequestDto registrationDto){
        try {
            logger.info("Get one user by Id");
            Optional<User> userOptional = userRepository.findById(userId);

            logger.info("Get one user by Id {}", userOptional);

            if(userOptional.isEmpty()){
                logger.debug("Could not find user");
                return ApiResponseUtil.toNotFoundApiResponse(null, "User not found");
            }

            User user = userOptional.get();

            user.setFullName(registrationDto.getFullName());
            user.setEmail(registrationDto.getEmail());
            user.setEnabled(registrationDto.isEnabled());
            user.setAccountLocked(registrationDto.isAccountLocked());

            var response = userRepository.saveAndFlush(user);
            return ApiResponseUtil.toOkApiResponse(response, "Updated user successfully");
        }catch(Exception exception){
            logger.error("error while processing login: {}", exception);
            ArrayList<ErrorDetails> error = new ArrayList<>();
            error.add(new ErrorDetails(exception.getMessage(), exception.toString()));
            return ApiResponseUtil.toBadRequestApiResponse("Error", error);
        }
    }

    public IApiResponse<?> processAccountStatusChange(String id, String status){
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

    public IApiResponse<?> processDeleteUser(int userId){
        try {
            logger.info("Processing login authentication");
            logger.info("Get one user by Id");
            Optional<User> userOptional = userRepository.findById(userId);

            logger.info("Get one user by Id {}", userOptional);

            if(userOptional.isEmpty()){
                logger.debug("Could not find user");
                return ApiResponseUtil.toNotFoundApiResponse(null, "User not found");
            }

            User user = userOptional.get();
            user.setAccountLocked(true);

            var response = userRepository.saveAndFlush(user);
            return ApiResponseUtil.toOkApiResponse(response, "User account blocked successfully");
        }catch(Exception exception){
            logger.error("error while processing login: {}", exception);
            ArrayList<ErrorDetails> error = new ArrayList<>();
            error.add(new ErrorDetails(exception.getMessage(), exception.toString()));
            return ApiResponseUtil.toBadRequestApiResponse("Error", error);
        }
    }
}
