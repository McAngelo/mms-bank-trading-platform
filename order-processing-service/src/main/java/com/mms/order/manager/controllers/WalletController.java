package com.mms.order.manager.controllers;

import com.mms.order.manager.dtos.requests.CreateWalletDto;
import com.mms.order.manager.dtos.responses.PortfolioDto;
import com.mms.order.manager.exceptions.WalletException;
import com.mms.order.manager.helpers.ApiResponse;
import com.mms.order.manager.services.interfaces.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/wallet/")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @PostMapping()
    public ResponseEntity<ApiResponse> createWallet(@RequestBody CreateWalletDto walletDto) throws WalletException {
        walletService.createWallet(walletDto.userId(), walletDto.balance());

        var response = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Successfully created wallet")
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserPortfolios(@PathVariable("userId") long userId) throws WalletException {
        BigDecimal wallet = walletService.getBalanceByUserId(userId)
                .orElseThrow(() -> new WalletException("Wallet not found"));

        return ResponseEntity.ok(ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(wallet)
                .build()
        );
    }
}
