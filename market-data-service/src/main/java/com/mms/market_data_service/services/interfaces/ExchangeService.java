package com.mms.market_data_service.services.interfaces;


import com.mms.market_data_service.dtos.responses.ProductData;
import com.mms.market_data_service.exceptions.ExchangeException;
import com.mms.market_data_service.dtos.responses.OrderBookDto;

import java.util.List;
import java.util.Optional;

public interface ExchangeService {
    List<OrderBookDto> getOrderBookFromExchange(
            String exchange, Optional<String> product, Optional<String> specifier
    ) throws ExchangeException;

    List<ProductData> getProductDataFromExchange(String exchange) throws ExchangeException;
}
