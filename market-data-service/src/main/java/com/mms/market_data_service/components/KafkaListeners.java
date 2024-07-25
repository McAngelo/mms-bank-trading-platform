package com.mms.market_data_service.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mms.market_data_service.dtos.responses.StreamOrder;
import com.mms.market_data_service.services.interfaces.DataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.kafka.annotation.KafkaListener;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaListeners {
    private final DataService dataService;

    @Value("${kafka.topic.exchange1}")
    private String topicNameForExchange1;

    @Value("${kafka.topic.exchange2}")
    private String topicNameForExchange2;

    @KafkaListener(topics = "${kafka.topic.exchange1}", groupId = "group-1", containerFactory = "kafkaListenerContainerFactory")
    void consumeExchange1(String order) {
        var optionalStreamOrder = parseToStreamOrder(order, topicNameForExchange1);

        optionalStreamOrder.ifPresent(dataService::ingestOrder);
    }

    @KafkaListener(topics = "${kafka.topic.exchange2}", groupId = "group-2", containerFactory = "kafkaListenerContainerFactory")
    void consumeExchange2(String order) {
       var optionalStreamOrder = parseToStreamOrder(order, topicNameForExchange2);

        optionalStreamOrder.ifPresent(dataService::ingestOrder);
    }

    private Optional<StreamOrder> parseToStreamOrder(String order, String topicName) {
        try {
            var objectMapper = new ObjectMapper();

            var streamOrder = objectMapper.readValue(order, StreamOrder.class);
            streamOrder.setDateTime(LocalDateTime.now());
            streamOrder.setTopicName(topicName);

            return Optional.of(streamOrder);
        } catch (Exception e) {
            log.warn("Failed to parse order{}", order);
            return Optional.empty();
        }
    }
}
