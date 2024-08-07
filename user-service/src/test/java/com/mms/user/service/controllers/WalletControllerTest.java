package com.mms.user.service.controllers;

//org.mockito.InjectMocks;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(WalletController.class)
@ExtendWith(MockitoExtension.class)

class WalletControllerTest {

   /* @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;

    @InjectMocks
    private WalletController walletController;

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

        when(walletService.processGetAllWallets(any(Integer.class), any(Integer.class)))
                .thenReturn(apiResponse);

        mockMvc.perform(get("/api/v1/wallet")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void testFindWalletById() throws Exception {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(200);
        // set other fields of apiResponse if necessary

        when(walletService.processGetOneWalletById(any(Integer.class)))
                .thenReturn(apiResponse);

        mockMvc.perform(get("/api/v1/wallet/details/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void testFindWalletByUserId() throws Exception {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(200);
        // set other fields of apiResponse if necessary

        when(walletService.processGetWalletsByUserId(any(Integer.class)))
                .thenReturn(apiResponse);

        mockMvc.perform(get("/api/v1/wallet/user/details/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void testSaveWallet() throws Exception {
        WalletRequestDTO requestDTO = new WalletRequestDTO();
        // populate requestDTO with necessary fields

        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(201);
        // set other fields of apiResponse if necessary

        when(walletService.processWalletCreation(any(WalletRequestDTO.class), any(Authentication.class)))
                .thenReturn(apiResponse);

        mockMvc.perform(post("/api/v1/wallet/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201));
    }

    @Test
    void testUpdateWallet() throws Exception {
        WalletRequestDTO requestDTO = new WalletRequestDTO();
        // populate requestDTO with necessary fields

        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(200);
        // set other fields of apiResponse if necessary

        when(walletService.processUpdateWallet(any(Integer.class), any(WalletRequestDTO.class), any(Authentication.class)))
                .thenReturn(apiResponse);

        mockMvc.perform(put("/api/v1/wallet/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void testDeleteWallet() throws Exception {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(200);
        // set other fields of apiResponse if necessary

        when(walletService.processDeleteWallet(any(Integer.class)))
                .thenReturn(apiResponse);

        mockMvc.perform(delete("/api/v1/wallet/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }*/

}