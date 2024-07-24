package com.mms.market_data_service.controllers;

import com.google.gson.Gson;
import com.mms.market_data_service.config.AppProperties;
import com.mms.market_data_service.config.RedisConfig;
import com.mms.market_data_service.helper.ApiResponse;
import com.mms.market_data_service.helper.ApiResponseUtil;
import com.mms.market_data_service.helper.Error;
import com.mms.market_data_service.helper.ExternalApiRequests;
import com.mms.market_data_service.models.Order;
import com.mms.market_data_service.repositories.MarketFeedRepository;
import com.mms.market_data_service.services.MarketDataSocketServer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@RequestMapping("api/v1/exchange-connection")
@RestController
public class ExchangeController {
    private static final Logger logger = LoggerFactory.getLogger(ExchangeController.class);
    private final AppProperties appProperties;
    private final Gson gson = new Gson();

    RestTemplate restTemplate; // we can use this to make http requests

    @Autowired
    private MarketFeedRepository marketFeedRepository;

    public ExchangeController(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @PostMapping("subscribe/{exchangeId}")
    public ApiResponse<String> subscribe(@PathVariable("exchangeId") String exchangeId, @Valid String callBackUrl){
        // validation
        validateExchangeUrl(exchangeId);
        try {
            logger.info("POST to subscribe/{} endpoint", exchangeId);
            ExternalApiRequests apiRequests = new ExternalApiRequests(exchangeId);
            var response = apiRequests.postRequests("pd/subscription", callBackUrl, "text/plain");
            logger.info("exchange subscription endpoint response: {}", response);
            return ApiResponseUtil.toOkApiResponse(response.body(), "Successful Subscription to the first exchange");
        }catch(Exception exception){
            logger.error("exchange subscription endpoint error response: {}", exception);
            ArrayList<Error> error = new ArrayList<>();
            error.add(new Error(exception.toString(), exception.getMessage()));
            return ApiResponseUtil.toBadRequestApiResponse(null, "Error", error);
        }
    }

    @PutMapping("update-subscribe/{exchangeId}")
    public ApiResponse<String> updateSubscribe(@PathVariable("exchangeId") String exchangeId,  @Valid String callBackUrl){
        validateExchangeUrl(exchangeId);
        try {
            logger.info("PUT to update-subscribe/{} endpoint", exchangeId);
            ExternalApiRequests apiRequests = new ExternalApiRequests(exchangeId);
            var response = apiRequests.putRequests("pd/subscription", "");
            logger.info("exchange subscription update endpoint response: {}", response);
            return ApiResponseUtil.toOkApiResponse(response.body(), "Successful Subscription to the first exchange");
        }catch(Exception exception){
            logger.error("exchange subscription update endpoint error response: {}", exception);
            ArrayList<Error> error = new ArrayList<>();
            error.add(new Error(exception.toString(), exception.getMessage()));
            return ApiResponseUtil.toBadRequestApiResponse(null, "Error", error);
        }
    }

    @DeleteMapping("unsubscribe/{exchangeId}")
    public ApiResponse<String> unsubscribe(@PathVariable("exchangeId") String exchangeId,  @Valid String callBackUrl){
        validateExchangeUrl(exchangeId);
        try {
            logger.info("DELETE to unsubscribe/{} endpoint", exchangeId);
            ExternalApiRequests apiRequests = new ExternalApiRequests(exchangeId);
            var response = apiRequests.deleteRequests("pd/subscription", callBackUrl);
            logger.info("exchange unsubscription update endpoint response: {}", response);
            return ApiResponseUtil.toOkApiResponse(response.body(), "Successful Subscription to the first exchange");
        }catch(Exception exception){
            logger.error("exchange subscription update endpoint error response: {}", exception);
            ArrayList<Error> error = new ArrayList<>();
            error.add(new Error(exception.toString(), exception.getMessage()));
            return ApiResponseUtil.toBadRequestApiResponse(null, "Error", error);
        }
    }

    //
    @PostMapping("callback-url")
    public ApiResponse<Object> callbackUrl(@RequestBody @Valid Order order){
        logger.info("Callback from the exchange: {}", order);
        try {
            // Broadcast to all listening services
            logger.info("Broadcast order to all listening services");
            MarketDataSocketServer.broadcastOrder(order);
            // Save to Redis cache
            logger.info("Save to Redis cache");
            Order response = marketFeedRepository.save(order);
            return ApiResponseUtil.toOkApiResponse(order, "Successful");
        }catch(Exception exception){
            logger.error("call back process error response: {}", exception);
            ArrayList<Error> error = new ArrayList<>();
            error.add(new Error(exception.toString(), exception.getMessage()));
            return ApiResponseUtil.toBadRequestApiResponse(null, "Error", error);
        }
    }

    private ApiResponse<String> validateExchangeUrl(String exchangeId){
        if (appProperties.getExchanges().contains(exchangeId)) {
            return ApiResponseUtil.toBadRequestApiResponse(null, "Invalid exchange Id", new ArrayList<>());
        }
        return null;
    }



}
