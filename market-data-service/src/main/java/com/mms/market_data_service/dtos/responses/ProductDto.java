package com.mms.market_data_service.dtos.responses;

public record ProductDto (
        String TICKER,
        double SELL_LIMIT,
        double LAST_TRADED_PRICE,
        double ASK_PRICE,
        double BID_PRICE,
        double BUY_LIMIT
) { }
