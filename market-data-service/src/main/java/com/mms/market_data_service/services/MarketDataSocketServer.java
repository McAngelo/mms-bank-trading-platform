package com.mms.market_data_service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mms.market_data_service.models.Order;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import static com.mms.market_data_service.helper.JsonHelper.convertOrderToJson;


@ServerEndpoint("/market-data-updates")
public class MarketDataSocketServer {
    private static final Set<Session> sessions = new CopyOnWriteArraySet<>();

    public static void broadcastOrder(Order order) {
        String message = convertOrderToJson(order);
        System.out.println(message);
        synchronized (sessions) {
            for (Session session : sessions) {
                if (session.isOpen()) {
                    try {
                        session.getBasicRemote().sendText(message);
                    } catch (IOException e) {
                        // Log the error and possibly remove the session
                        System.err.println("Error broadcasting to session: " + e.getMessage());
                        sessions.remove(session);
                    }
                }
            }
        }
    }



    // This method would be called when a new WebSocket connection is established
    public void onOpen(Session session) {
        sessions.add(session);
    }

    // This method would be called when a WebSocket connection is closed
    public void onClose(Session session) {
        sessions.remove(session);
    }
}
