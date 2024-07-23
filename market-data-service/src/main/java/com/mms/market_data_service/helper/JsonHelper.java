package com.mms.market_data_service.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mms.market_data_service.models.Order;

public class JsonHelper {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static String convertOrderToJson(Order order) {
        try {
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            // Log the error
            System.err.println("Error converting order to JSON: " + e.getMessage());
            // In a real application, you might want to throw a custom exception here
            return "{}"; // Return empty JSON object as fallback
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) throws JsonProcessingException {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonMappingException e) {
            throw new JsonProcessingException("Error mapping JSON to object", e) {};
        }
    }

    public static String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
