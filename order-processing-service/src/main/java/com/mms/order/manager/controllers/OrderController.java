package com.mms.order.manager.controllers;

import com.mms.order.manager.dtos.requests.CreateOrderDto;
import com.mms.order.manager.exceptions.ExchangeException;
import com.mms.order.manager.exceptions.MarketDataException;
import com.mms.order.manager.exceptions.OrderException;
import com.mms.order.manager.helpers.ApiResponse;
import com.mms.order.manager.services.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping()
    public ResponseEntity<ApiResponse> createOrder(@RequestBody CreateOrderDto orderDto) throws ExchangeException, OrderException, MarketDataException {
        orderService.createOrder(orderDto);

        var response = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Successfully created order")
                .build();

        return ResponseEntity.ok(response);
    }
}
