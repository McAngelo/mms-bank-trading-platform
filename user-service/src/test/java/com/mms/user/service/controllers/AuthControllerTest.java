package com.mms.user.service.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.mms.user.service.dtos.AuthenticationRequestDto;
import com.mms.user.service.dtos.RegistrationRequestDto;
import com.mms.user.service.helper.ApiResponse;
import com.mms.user.service.services.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AuthController.class)
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService service;

    @InjectMocks
    private AuthController authController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testRegister() throws Exception {
        /*RegistrationRequestDto registrationDto = new RegistrationRequestDto();
        // populate registrationDto with necessary fields

        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(201);
        // set other fields of apiResponse if necessary

        when(service.register(any(RegistrationRequestDto.class))).thenReturn(apiResponse);

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201));*/
    }

    @Test
    void testLogin() throws Exception {
        /*AuthenticationRequestDto authenticationDto = new AuthenticationRequestDto();
        // populate authenticationDto with necessary fields

        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(201);
        // set other fields of apiResponse if necessary

        when(service.authenticate(any(AuthenticationRequestDto.class))).thenReturn(apiResponse);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201));*/
    }
}