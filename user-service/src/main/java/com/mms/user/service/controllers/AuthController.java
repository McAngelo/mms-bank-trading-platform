package com.mms.user.service.controllers;

import com.mms.user.service.helper.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/auth")
@RestController
public class AuthController {
    ApiResponse<String> apiResponse = new ApiResponse<>();

    @PostMapping("/register")
    public ApiResponse<String> register(){
        apiResponse.setMessage("Register successful");
        apiResponse.setStatus(200);
        return apiResponse;
    }


    @PostMapping("/authenticate-user")
    public ApiResponse<String> login(){
        apiResponse.setMessage("Login successful");
        apiResponse.setStatus(200);
        return apiResponse;
    }

    @PostMapping("/verify")
    public ApiResponse<String> validate(){
        apiResponse.setMessage("User Account verified successfully");
        apiResponse.setStatus(200);
        return apiResponse;
    }
}
