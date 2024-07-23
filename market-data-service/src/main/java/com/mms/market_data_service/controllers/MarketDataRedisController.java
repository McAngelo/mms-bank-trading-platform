package com.mms.market_data_service.controllers;

import com.mms.market_data_service.helper.ApiResponse;
import com.mms.market_data_service.helper.ApiResponseUtil;
import com.mms.market_data_service.helper.Error;
import com.mms.market_data_service.models.Order;
import com.mms.market_data_service.repositories.MarketFeedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/market-feed")
public class MarketDataRedisController {
    private static final Logger logger = LoggerFactory.getLogger(MarketDataRedisController.class);

    @Autowired
    private RedisTemplate<String, String> template;
    @Autowired
    private MarketFeedRepository marketFeedRepository;

    @GetMapping("/all")
    public ApiResponse<Iterable<Order>> allOrders() {
        logger.info("GET all order from  endpoint");
        try {
            Iterable<Order> response =  marketFeedRepository.findAll();
            logger.info("response form all order endpoint", response);
            return ApiResponseUtil.toOkApiResponse(response, "Retrieved all orders successfully");
        }catch(Exception exception){
            logger.error("all order error response: {}", exception);
            ArrayList<Error> error = new ArrayList<>();
            error.add(new Error(exception.toString(), exception.getMessage()));
            return ApiResponseUtil.toBadRequestApiResponse(null, "Error", error);
        }
    }

    @GetMapping("/exchange/{exchangeName}")
    public ApiResponse<Iterable<Order>> ordersByExchange(@PathVariable String exchangeName) {
        logger.info("GET all order from " + exchangeName +" endpoint");
        try {
            List<Order> response = marketFeedRepository.findByExchange(exchangeName);
            logger.info("response form all order from one exhange endpoint", response);
            return ApiResponseUtil.toOkApiResponse(response, "Retrived all orders by "+ exchangeName +" exchange");
        }catch(Exception exception){
            logger.error("exhange order error response: {}", exception);
            ArrayList<Error> error = new ArrayList<>();
            error.add(new Error(exception.toString(), exception.getMessage()));
            return ApiResponseUtil.toBadRequestApiResponse(null, "Error", error);
        }
    }

    @GetMapping("/order/{orderId}")
    public ApiResponse<Iterable<Order>> orderById(@PathVariable String orderId) {
        logger.info("GET all order from order id " + orderId);
        try {
            List<Order> response = marketFeedRepository.findByOrderID(orderId);
            logger.info("response form order id endpoint", response);
            return ApiResponseUtil.toOkApiResponse(response, "Retrived order by order Id "+ orderId);
        }catch(Exception exception){
            logger.error("order id error response: {}", exception);
            ArrayList<Error> error = new ArrayList<>();
            error.add(new Error(exception.toString(), exception.getMessage()));
            return ApiResponseUtil.toBadRequestApiResponse(null, "Error", error);
        }
    }

    @GetMapping("/product-orders/{productName}")
    public ApiResponse<Iterable<Order>> orderByProductName(@PathVariable String productName) {
        logger.info("GET all order from product name " + productName);
        try {
            List<Order> response = marketFeedRepository.findByProduct(productName);
            logger.info("response form order id endpoint", response);
            return ApiResponseUtil.toOkApiResponse(response, "Retrived orders by "+ productName + " product name");
        }catch(Exception exception){
            logger.error("product order error response: {}", exception);
            ArrayList<Error> error = new ArrayList<>();
            error.add(new Error(exception.toString(), exception.getMessage()));
            return ApiResponseUtil.toBadRequestApiResponse(null, "Error", error);
        }
    }
}
