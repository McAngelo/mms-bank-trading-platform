package com.mms.user.service.controllers;

import com.mms.user.service.dtos.*;
import com.mms.user.service.helper.ApiResponse;
import com.mms.user.service.helper.IApiResponse;
import com.mms.user.service.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@Tag(name = "Authentication")
public class AuthController {

    @Autowired
    private final AuthenticationService service;

    @Operation(summary = "Create a user", description = "Create a user in the system", tags = {"Authentication"})
    @ApiResponses(value = {@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Successfully created a user", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class, type = "object", anyOf = {RegistrationRequestDto.class}))}),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request payload", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))})})
    @PostMapping(value="/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<IApiResponse<?>> register(@RequestBody RegistrationRequestDto registrationDto) throws MessagingException {

        var result = service.register(registrationDto);
        ResponseEntity.BodyBuilder bd = ResponseEntity.status(result.getStatus());
        return bd.body(result);
    }

    @PostMapping(value="/login")
    public ResponseEntity<IApiResponse<?>> login(@RequestBody AuthenticationRequestDto request){
        var result = service.authenticate(request);
        ResponseEntity.BodyBuilder bd = ResponseEntity.status(result.getStatus());
        return  bd.body(result);
    }

//    @PostMapping("/verify")
//    public ApiResponse verifyUser(@RequestBody VerificationDto verificationDto){
//        return authService.processVerification(verificationDto);
//    }
//
//    @PostMapping("/forgotten-password")
//    public ApiResponse sendPasswordResettoken(@RequestBody String email){
//        return authService.processPasswordrestToken(email);
//    }
//
//    @PostMapping("/password-reset")
//    public ApiResponse passwordReset(@RequestBody ForgottenPasswordResetDto forgottenPasswordResetDto){
//        return authService.processPasswordrest(forgottenPasswordResetDto);
//    }
}
