package com.mms.user.service.controllers;

import com.mms.user.service.dtos.AuthenticationRequestDto;
import com.mms.user.service.dtos.AuthenticationResponseDto;
import com.mms.user.service.dtos.RegistrationRequestDto;
import com.mms.user.service.services.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v2/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication-RnD")
public class AuthenticationController {

    private final AuthenticationService service;

//    @PostMapping("/register")
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    public ResponseEntity<?> register(@RequestBody @Valid RegistrationRequestDto request) throws MessagingException {
//        System.out.println("THIS REQUEST " +request);
//        service.register(request);
//        return ResponseEntity.accepted().build();
//    }

//    @PostMapping("/authenticate")
//    public ResponseEntity<AuthenticationResponseDto> authenticate(
//            @RequestBody AuthenticationRequestDto request
//    ) {
//        return ResponseEntity.ok(service.authenticate(request));
//    }
//    @GetMapping("/activate-account")
//    public void confirm(
//            @RequestParam String token
//    ) throws MessagingException {
//        service.activateAccount(token);
//    }
}
