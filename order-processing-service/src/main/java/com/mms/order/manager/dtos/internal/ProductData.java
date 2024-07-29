package com.mms.order.manager.dtos.internal;

import com.fasterxml.jackson.annotation.JsonProperty;


public record ProductData(
        @JsonProperty("TICKER")
        String ticker,

        @JsonProperty("SELL_LIMIT")
        int sellLimit,

        @JsonProperty("LAST_TRADED_PRICE")
        double lastTradedPrice,

        @JsonProperty("ASK_PRICE")
        double askPrice,

        @JsonProperty("BID_PRICE")
        double bidPrice,

        @JsonProperty("BUY_LIMIT")
        int buyLimit,

        @JsonProperty("MAX_PRICE_SHIFT")
        double maxPriceShift
) {
        @Override
        public String toString() {
                return "{" +
                        "ticker='" + ticker + '\'' +
                        ", sellLimit=" + sellLimit +
                        ", lastTradedPrice=" + lastTradedPrice +
                        ", askPrice=" + askPrice +
                        ", bidPrice=" + bidPrice +
                        ", buyLimit=" + buyLimit +
                        ", maxPriceShift=" + maxPriceShift +
                        '}';
        }
}
