/*
package com.mms.user.service.controllers;

import com.mms.user.service.dtos.WalletRequestDTO;
import com.mms.user.service.services.WalletService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/wallet")
@RequiredArgsConstructor
@Tag(name = "Wallet")
public class WalletController {

    private final WalletService walletService;

    @GetMapping
    public ResponseEntity<?> findAllBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        var result = walletService.processGetAllWallets(page, size);

        ResponseEntity.BodyBuilder bd = ResponseEntity.status(result.getStatus());
        return bd.body(result);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<?> findWalletById(
            @PathVariable("id") int WalletId
    ) {
        var result = walletService.processGetOneWalletById(WalletId);
        ResponseEntity.BodyBuilder bd = ResponseEntity.status(result.getStatus());
        return bd.body(result);
    }
    @GetMapping("/user/details/{id}")
    public ResponseEntity<?> findWalletByUserId(
            @PathVariable("id") int userId
    ) {
        var result = walletService.processGetWalletsByUserId(userId);
        ResponseEntity.BodyBuilder bd = ResponseEntity.status(result.getStatus());
        return bd.body(result);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveWallet(
            @Valid @RequestBody WalletRequestDTO request,
            Authentication connectedUser
    ) {
        var result = walletService.processWalletCreation(request, connectedUser);
        ResponseEntity.BodyBuilder bd = ResponseEntity.status(result.getStatus());
        return bd.body(result);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateWallet(
            @PathVariable("id") int WalletId,
            @Valid @RequestBody WalletRequestDTO request,
            Authentication connectedUser
    ) {
        var result = walletService.processUpdateWallet(WalletId, request, connectedUser);
        ResponseEntity.BodyBuilder bd = ResponseEntity.status(result.getStatus());
        return bd.body(result);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int WalletId){
        var result = walletService.processDeleteWallet(WalletId);
        ResponseEntity.BodyBuilder bd = ResponseEntity.status(result.getStatus());
        return bd.body(result);
    }
}

*/
