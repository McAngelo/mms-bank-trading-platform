package com.mms.user.service.controllers;

import com.mms.user.service.dtos.ForgottenPasswordResetDto;
import com.mms.user.service.dtos.LoginDto;
import com.mms.user.service.dtos.RegistrationDto;
import com.mms.user.service.dtos.VerificationDto;
import com.mms.user.service.helper.ApiResponse;
import com.mms.user.service.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/auth")
@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ApiResponse register(@RequestBody RegistrationDto registrationDto){
        return authService.registration(registrationDto);
    }

    @PostMapping("/authenticate-user")
    public ApiResponse login(@RequestBody LoginDto loginDto){
        return authService.processLogin(loginDto);
    }

    @PostMapping("/verify")
    public ApiResponse verifyUser(@RequestBody VerificationDto verificationDto){
        return authService.processVerification(verificationDto);
    }

    @PostMapping("/forgotten-password")
    public ApiResponse sendPasswordResettoken(@RequestBody String email){
        return authService.processPasswordrestToken(email);
    }

    @PostMapping("/password-reset")
    public ApiResponse passwordReset(@RequestBody ForgottenPasswordResetDto forgottenPasswordResetDto){
        return authService.processPasswordrest(forgottenPasswordResetDto);
    }
}
