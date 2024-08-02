package com.mms.market_data_service.services.impl;

import com.google.gson.Gson;
import com.mms.market_data_service.config.AppProperties;
import com.mms.market_data_service.helper.ApiResponse;
import com.mms.market_data_service.helper.ApiResponseUtil;
import com.mms.market_data_service.helper.Error;
import com.mms.market_data_service.helper.ExternalApiRequests;
import com.mms.market_data_service.models.Order;
import com.mms.market_data_service.repositories.MarketFeedRepository;
import com.mms.market_data_service.services.interfaces.MarketExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class MarketExchangeServiceImpl implements MarketExchangeService {
    private static final Logger logger = LoggerFactory.getLogger(MarketExchangeServiceImpl.class);
    private final AppProperties appProperties;
    private final Gson gson = new Gson();

    RestTemplate restTemplate; // we can use this to make http requests
    private final SimpMessagingTemplate messagingTemplate;

    private final MarketFeedRepository marketFeedRepository;

    public MarketExchangeServiceImpl(AppProperties appProperties, SimpMessagingTemplate messagingTemplate, MarketFeedRepository marketFeedRepository) {
        this.appProperties = appProperties;
        this.messagingTemplate = messagingTemplate;
        this.marketFeedRepository = marketFeedRepository;
    }

    @Override
    public ApiResponse<String> subscribe(String exchangeId, String callBackUrl){
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

    @Override
    public ApiResponse<String> updateSubscribe(String exchangeId, String callBackUrl){
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

    @Override
    public ApiResponse<String> unsubscribe(String exchangeId, String callBackUrl){
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

    @Override
    public ApiResponse<Object> callbackUrl(Order order){
        logger.info("Callback from the exchange: {}", order);
        try {
            // Broadcast to all listening services
            logger.info("Broadcast order to all listening services");
//            MarketDataSocketServer.broadcastOrder(order);

            logger.info("Message to broadcast: {}", order);

            messagingTemplate.convertAndSend("/queue/new-order", order);

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
