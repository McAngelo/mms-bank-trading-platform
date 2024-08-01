package com.mms.user.service.controllers;

import com.mms.user.service.dtos.BookRequestDto;
import com.mms.user.service.dtos.BookResponseDto;
import com.mms.user.service.dtos.BorrowedBookResponseDto;
import com.mms.user.service.dtos.PortfolioRequestDTO;
import com.mms.user.service.helper.PageResponse;
import com.mms.user.service.model.Portfolio;
import com.mms.user.service.services.BookService;
import com.mms.user.service.services.PortfolioService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("portfolio")
@RequiredArgsConstructor
@Tag(name = "Portfolio")
public class PortfolioController {

    private final BookService service;
    private final PortfolioService portfolioService;

    @GetMapping
    public ResponseEntity<?> findAllBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        var result = portfolioService.processGetAllPortfolios(page, size);

        ResponseEntity.BodyBuilder bd = ResponseEntity.status(result.getStatus());
        return bd.body(result);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<?> findPortfolioById(
            @PathVariable("id") int portfolioId
    ) {
        var result = portfolioService.processGetOnePortfolioById(portfolioId);

        ResponseEntity.BodyBuilder bd = ResponseEntity.status(result.getStatus());
        return bd.body(result);
    }
    @GetMapping("/user/details/{id}")
    public ResponseEntity<?> findPortfolioByUserId(
            @PathVariable("id") int userId
    ) {
        var result = portfolioService.processGetPortfoliosByUserId(userId);
        ResponseEntity.BodyBuilder bd = ResponseEntity.status(result.getStatus());
        return bd.body(result);
    }

    @PostMapping("/save")
    public ResponseEntity<?> savePortfolio(
            @Valid @RequestBody PortfolioRequestDTO request,
            Authentication connectedUser
    ) {
        var result = portfolioService.processPortfolioCreation(request, connectedUser);
        ResponseEntity.BodyBuilder bd = ResponseEntity.status(result.getStatus());
        return bd.body(result);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePortfolio(
            @PathVariable("id") int portfolioId,
            @Valid @RequestBody PortfolioRequestDTO request,
            Authentication connectedUser
    ) {
        var result = portfolioService.processUpdatePortfolio(portfolioId, request, connectedUser);
        ResponseEntity.BodyBuilder bd = ResponseEntity.status(result.getStatus());
        return bd.body(result);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int portfolioId){
        var result = portfolioService.processDeletePortfolio(portfolioId);
        ResponseEntity.BodyBuilder bd = ResponseEntity.status(result.getStatus());
        return bd.body(result);
    }
}
