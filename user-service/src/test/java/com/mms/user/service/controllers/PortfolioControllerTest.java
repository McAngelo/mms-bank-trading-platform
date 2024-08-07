package com.mms.user.service.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mms.user.service.helper.ApiResponse;
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
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PortfolioController.class)
@ExtendWith(MockitoExtension.class)

class PortfolioControllerTest {
    /*@Autowired
    private MockMvc mockMvc;

    @MockBean
    private PortfolioService portfolioService;

    @InjectMocks
    private PortfolioController portfolioController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testFindAllBooks() throws Exception {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(200);
        // set other fields of apiResponse if necessary

        when(portfolioService.processGetAllPortfolios(any(Integer.class), any(Integer.class)))
                .thenReturn(apiResponse);

        mockMvc.perform(get("/api/v1/portfolio")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void testFindPortfolioById() throws Exception {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(200);
        // set other fields of apiResponse if necessary

        when(portfolioService.processGetOnePortfolioById(any(Integer.class)))
                .thenReturn(apiResponse);

        mockMvc.perform(get("/api/v1/portfolio/details/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void testFindPortfolioByUserId() throws Exception {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(200);
        // set other fields of apiResponse if necessary

        when(portfolioService.processGetPortfoliosByUserId(any(Integer.class)))
                .thenReturn(apiResponse);

        mockMvc.perform(get("/api/v1/portfolio/user/details/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void testSavePortfolio() throws Exception {
        PortfolioRequestDTO requestDTO = new PortfolioRequestDTO();
        // populate requestDTO with necessary fields

        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(201);
        // set other fields of apiResponse if necessary

        when(portfolioService.processPortfolioCreation(any(PortfolioRequestDTO.class), any(Authentication.class)))
                .thenReturn(apiResponse);

        mockMvc.perform(post("/api/v1/portfolio/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201));
    }

    @Test
    void testUpdatePortfolio() throws Exception {
        PortfolioRequestDTO requestDTO = new PortfolioRequestDTO();
        // populate requestDTO with necessary fields

        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(200);
        // set other fields of apiResponse if necessary

        when(portfolioService.processUpdatePortfolio(any(Integer.class), any(PortfolioRequestDTO.class), any(Authentication.class)))
                .thenReturn(apiResponse);

        mockMvc.perform(put("/api/v1/portfolio/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void testDeletePortfolio() throws Exception {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(200);
        // set other fields of apiResponse if necessary

        when(portfolioService.processDeletePortfolio(any(Integer.class)))
                .thenReturn(apiResponse);

        mockMvc.perform(delete("/api/v1/portfolio/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }*/
}