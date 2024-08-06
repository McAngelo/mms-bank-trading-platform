package com.mms.market_data_service.services.interfaces;

import com.mms.market_data_service.helper.ApiResponse;
import com.mms.market_data_service.models.Order;

public interface MarketExchangeService {
    ApiResponse<String> subscribe(String exchangeId, String callBackUrl);
    ApiResponse<String> updateSubscribe(String exchangeId, String callBackUrl);
    ApiResponse<String> unsubscribe(String exchangeId, String callBackUrl);
    ApiResponse<Object> callbackUrl(Order order);
}
