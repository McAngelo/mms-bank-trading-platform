package com.mms.order.manager.controllers;

import com.mms.order.manager.dtos.requests.CreateWalletDto;
import com.mms.order.manager.exceptions.WalletException;
import com.mms.order.manager.helpers.ApiResponse;
import com.mms.order.manager.services.interfaces.WalletService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/wallet")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @PostMapping()
    public ResponseEntity<ApiResponse> createWallet(@RequestBody CreateWalletDto walletDto) throws WalletException {
        walletService.createWallet(walletDto.userId(), walletDto.balance());

        var response = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Successfully created order")
                .build();

        return ResponseEntity.ok(response);
    }
}
