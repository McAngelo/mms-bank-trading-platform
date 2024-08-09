package com.mms.user.service.services;

import com.mms.user.service.dtos.ApiResponseDTO;
import com.mms.user.service.dtos.PortfolioRequestDTO;
import com.mms.user.service.dtos.WalletRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderMicroServiceHttpClient {
    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8084/api/v1";

    public void createWallet(WalletRequestDTO walletRequestDTO) {
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/wallet/", walletRequestDTO, String.class);
        log.info("Wallet Creation Api Response {}", response);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to fetch order book data from exchange");
        }

        log.info("Successfully created a wallet");
    }

    public void createPortfolio(PortfolioRequestDTO portfolioRequestDTO) {
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/portfolios", portfolioRequestDTO, String.class);
        log.info("Portfolio Creation Api Response {}", response);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to fetch order book data from exchange");
        }

        log.info("Successfully created a portfolio");    }

}
