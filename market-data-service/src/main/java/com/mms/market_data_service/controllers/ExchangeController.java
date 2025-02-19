package com.mms.market_data_service.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mms.market_data_service.exceptions.ExchangeException;
import com.mms.market_data_service.helper.ApiResponse;
import com.mms.market_data_service.models.Order;
import com.mms.market_data_service.publishers.OrderPublisher;
import com.mms.market_data_service.services.interfaces.MarketExchangeService;
import com.mms.market_data_service.tasks.ScheduledTasks;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("api/v1/exchange-connection")
@RestController
@RequiredArgsConstructor
public class ExchangeController {
    private static final Logger logger = LoggerFactory.getLogger(ExchangeController.class);
    private final MarketExchangeService marketExchangeService;
    private final ScheduledTasks scheduledTasks;
    private final OrderPublisher orderPublisher;


    @PostMapping("subscribe/{exchangeId}")
    public ResponseEntity<ApiResponse<String>> subscribe(@PathVariable("exchangeId") String exchangeId, @Valid String callBackUrl) {
        var response = marketExchangeService.subscribe(exchangeId, callBackUrl);
        logger.info("subscribe response: {}", response);
        return ResponseEntity.status(response.status()).body(response);
    }

    @PutMapping("update-subscribe/{exchangeId}")
    public ResponseEntity<ApiResponse<String>> updateSubscribe(@PathVariable("exchangeId") String exchangeId, @Valid String callBackUrl) {
        var response = marketExchangeService.updateSubscribe(exchangeId, callBackUrl);
        logger.info("updateSubscribe response: {}", response);
        return ResponseEntity.status(response.status()).body(response);
    }

    @DeleteMapping("unsubscribe/{exchangeId}")
    public ResponseEntity<ApiResponse<String>> unsubscribe(@PathVariable("exchangeId") String exchangeId, @Valid String callBackUrl) {
        var response = marketExchangeService.unsubscribe(exchangeId, callBackUrl);
        logger.info("unsubscribe response: {}", response);
        return ResponseEntity.status(response.status()).body(response);
    }

    //
    @PostMapping("callback-url")
    public ResponseEntity<ApiResponse<Object>> callbackUrl(@RequestBody @Valid Order order) {
        var response = marketExchangeService.callbackUrl(order);
        logger.info("callbackUrl response: {}", response);
        return ResponseEntity.status(response.status()).body(response);
    }

    @PostMapping("callback-url/{exchangeSlug}")
    public void handleCallbackFromExchange(
            @PathVariable("exchangeSlug") String exchangeSlug,
            @RequestBody @Valid Order order
    ) throws ExchangeException {
        orderPublisher.publishToOrderService(order.getOrderID().toString(), exchangeSlug);
        scheduledTasks.cacheOrderBook();
    }
}
