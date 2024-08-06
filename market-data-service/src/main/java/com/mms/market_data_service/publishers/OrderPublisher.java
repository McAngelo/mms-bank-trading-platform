package com.mms.market_data_service.publishers;

import com.mms.market_data_service.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${spring.rabbitmq.exchange}")
    public String EXCHANGE = "main-exchange";

    @Value("${spring.rabbitmq.routing-key}")
    public String ROUTING_KEY;

    public void publishToOrderService(String exchangeOrderId, String exchangeSlug) {
        String key = String.format("pending-orders:%s", exchangeSlug);

        boolean isOrderFound = Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, exchangeOrderId));

        if (isOrderFound) {
            rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, exchangeOrderId);
            log.info("Sent pending order to queue to be updated");
        } else {
            log.info("order not found");
        }
    }
}
