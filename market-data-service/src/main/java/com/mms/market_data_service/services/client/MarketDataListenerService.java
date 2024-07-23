package com.mms.market_data_service.services.client;

import com.mms.market_data_service.models.Order;
import static com.mms.market_data_service.helper.JsonHelper.fromJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

@ClientEndpoint
public class MarketDataListenerService {
    private static final Logger logger = LoggerFactory.getLogger(MarketDataListenerService.class);
    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        logger.info("Connected to market data server");
    }

    @OnMessage
    public void onMessage(String message) {
        logger.info("Received market data update: {}", message);
        // Process the received order data
        processOrderUpdate(message);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info("Disconnected from market data server. Reason: {}", closeReason.getReasonPhrase());
        this.session = null;
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.error("Error in market data connection", throwable);
    }

    private void processOrderUpdate(String orderJson) {
        // Implement your order processing logic here
        // This could involve parsing the JSON, updating a local cache, triggering other services, etc.
        // For example:
        try {
            Order order = fromJson(orderJson, Order.class);
            // Do something with the order...
            logger.info("Processed order update for order ID: {}", order.getId());
        } catch (Exception e) {
            logger.error("Error processing order update", e);
        }
    }

    public void connect(String serverUrl) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            logger.info("Connection ", container);
            container.connectToServer(this, new URI(serverUrl));
        } catch (Exception e) {
            logger.error("Error connecting to market data server", e);
        }
    }

    public void disconnect() {
        if (session != null && session.isOpen()) {
            try {
                session.close();
            } catch (IOException e) {
                logger.error("Error closing market data connection", e);
            }
        }
    }

    // This method could be called periodically to ensure connection
    public void ensureConnected(String serverUrl) {
        if (session == null || !session.isOpen()) {
            connect(serverUrl);
        }
    }
}
