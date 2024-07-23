package com.mms.user.service.controllers;

import com.mms.user.service.helper.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/users")
@RestController
public class UsersController {
    ApiResponse<String> apiResponse = new ApiResponse<>();

    @GetMapping
    public ApiResponse<String> getAllUser(){
        apiResponse.setMessage("Get All users, successfully");
        apiResponse.setStatus(200);
        return apiResponse;
    }


    @GetMapping("/{id}")
    public ApiResponse<String> getUserById(@PathVariable("id") String id){
        apiResponse.setMessage("Get only one user details, successfully");
        apiResponse.setStatus(200);
        return apiResponse;
    }

    @PostMapping
    public ApiResponse<String> addUser(){
        apiResponse.setMessage("Create a user record, successfully");
        apiResponse.setStatus(200);
        return apiResponse;
    }

    @PutMapping("{id}")
    public ApiResponse<String> updateUser(@PathVariable("id") String id){
        System.out.println(id);
        apiResponse.setMessage("Update user details, successfully");
        apiResponse.setStatus(200);
        return apiResponse;
    }

    @PutMapping("/change-account-status/{id}")
    public ApiResponse<String> accountStatusChange(@PathVariable("id") String id){
        System.out.println(id);
        apiResponse.setMessage("User Account changed successfully");
        apiResponse.setStatus(200);
        return apiResponse;
    }

    @DeleteMapping("{id}")
    public ApiResponse<String> deleteUser(@PathVariable("id") String id){
        System.out.println(id);
        apiResponse.setMessage("Delete User Account, successfully");
        apiResponse.setStatus(200);
        return apiResponse;
    }
}
