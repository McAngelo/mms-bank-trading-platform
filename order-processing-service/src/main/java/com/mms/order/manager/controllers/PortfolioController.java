package com.mms.order.manager.controllers;

import com.mms.order.manager.dtos.requests.CreatePortfolioDto;
import com.mms.order.manager.dtos.responses.GetOrderDto;
import com.mms.order.manager.dtos.responses.GetOrdersDto;
import com.mms.order.manager.exceptions.PortfolioException;
import com.mms.order.manager.helpers.ApiResponse;
import com.mms.order.manager.services.interfaces.OrderService;
import com.mms.order.manager.services.interfaces.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/portfolios")
@RequiredArgsConstructor
public class PortfolioController {
    private final PortfolioService portfolioService;
    private final OrderService orderService;

    @PostMapping()
    public ResponseEntity<ApiResponse> createPortfolio(@RequestBody CreatePortfolioDto portfolioDto) throws PortfolioException {
        portfolioService.createPortfolio(portfolioDto);

        return ResponseEntity.ok(ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Successfully created order")
                .build()
        );
    }

    @GetMapping("/{portfolioId}/orders")
    public ResponseEntity<ApiResponse> getAllOrders(@PathVariable("portfolioId") long portfolioId, @RequestParam int page, @RequestParam int size)  {
        List<GetOrdersDto> orders = orderService.getOrders(portfolioId, page, size);

        return ResponseEntity.ok(ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(orders)
                .build()
        );
    }


}
