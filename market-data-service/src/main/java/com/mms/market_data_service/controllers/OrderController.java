package com.mms.market_data_service.controllers;

import com.mms.market_data_service.exceptions.ExchangeException;
import com.mms.market_data_service.helper.ApiResponse;
import com.mms.market_data_service.services.interfaces.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final ExchangeService exchangeService;

    @GetMapping("/orderbook")
    public ResponseEntity<ApiResponse> getOrderBook(
            @RequestParam String exchange,
            @RequestParam(required = false) String product,
            @RequestParam(required = false) String specifier
    ) throws ExchangeException {
            var orderBooks = exchangeService.getOrderBookFromExchange(
                    exchange,
                    Optional.ofNullable(product),
                    Optional.ofNullable(specifier)
            );

        var response = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Successfully retrieved order book from exchange")
                .data(orderBooks)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/pd")
    public ResponseEntity<ApiResponse> getProductData(@RequestParam String exchange) throws ExchangeException {
        var orderBooks = exchangeService.getProductDataFromExchange(exchange);

        var response = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Successfully retrieved order book from exchange")
                .data(orderBooks)
                .build();

        return ResponseEntity.ok(response);
    }

}
