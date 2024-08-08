package com.mms.order.manager.controllers;

import com.mms.order.manager.dtos.requests.CreateOrderDto;
import com.mms.order.manager.dtos.responses.GetOrderDto;
import com.mms.order.manager.dtos.responses.GetOrdersDto;
import com.mms.order.manager.exceptions.ExchangeException;
import com.mms.order.manager.exceptions.MarketDataException;
import com.mms.order.manager.exceptions.OrderException;
import com.mms.order.manager.helpers.ApiResponse;
import com.mms.order.manager.services.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping()
    public ResponseEntity<ApiResponse> createOrder(@RequestBody CreateOrderDto orderDto) throws ExchangeException, OrderException, MarketDataException {
        orderService.createOrder(orderDto);

        return ResponseEntity.ok(ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Successfully created order")
                .build()
        );
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable("orderId") long orderId) throws OrderException {
        GetOrderDto order = orderService.getOrder(orderId);

        return ResponseEntity.ok(ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(order)
                .message("Successfully retrieved order")
                .build());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse> getAllOrders(@PathVariable("userId") long userId, @RequestParam int page, @RequestParam int size)  {
        List<GetOrdersDto> orders = orderService.getOrdersByUserId(userId, page, size);

        return ResponseEntity.ok(ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(orders)
                .build()
        );
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<ApiResponse> updateOrderById(@PathVariable("orderId") long orderId) throws OrderException, ExchangeException {
        orderService.updateOrderStatus(orderId);

        return ResponseEntity.ok(ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Successfully updated order")
                .build());
    }

    @PatchMapping("/eoi/{exchangeOrderId}")
    public ResponseEntity<ApiResponse> updateOrderByexchangeOrderId(@PathVariable("exchangeOrderId") String exchangeOrderId) throws OrderException, ExchangeException {
        orderService.updateOrderStatus(exchangeOrderId);

        return ResponseEntity.ok(ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Successfully updated order")
                .build());
    }
}
