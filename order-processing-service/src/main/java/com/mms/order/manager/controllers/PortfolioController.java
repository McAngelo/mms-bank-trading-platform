package com.mms.order.manager.controllers;

import com.mms.order.manager.dtos.requests.CreatePortfolioDto;
import com.mms.order.manager.exceptions.PortfolioException;
import com.mms.order.manager.helpers.ApiResponse;
import com.mms.order.manager.services.interfaces.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/portfolio")
@RequiredArgsConstructor
public class PortfolioController {
    private final PortfolioService portfolioService;

    @PostMapping()
    public ResponseEntity<ApiResponse> createPortfolio(@RequestBody CreatePortfolioDto portfolioDto) throws PortfolioException {
        portfolioService.createPortfolio(portfolioDto);

        var response = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Successfully created order")
                .build();

        return ResponseEntity.ok(response);
    }


}
