package com.mms.market_data_service.services.client;

import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class MarketDataManager {
    private static final String SERVER_URL = "ws://localhost:8082/market-data-updates";
    private final MarketDataListenerService listener = new MarketDataListenerService();

    @PostConstruct
    public void init() {
        listener.connect(SERVER_URL);
    }

    @PreDestroy
    public void cleanup() {
        listener.disconnect();
    }

    // You might want to call this method periodically to ensure connection
    public void checkConnection() {
        listener.ensureConnected(SERVER_URL);
    }
}
