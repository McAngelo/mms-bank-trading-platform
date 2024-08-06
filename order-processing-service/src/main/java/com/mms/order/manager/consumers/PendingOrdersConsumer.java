package com.mms.order.manager.consumers;

import com.mms.order.manager.exceptions.ExchangeException;
import com.mms.order.manager.exceptions.OrderException;
import com.mms.order.manager.services.interfaces.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@RequiredArgsConstructor
public class PendingOrdersConsumer {
    private final OrderService orderService;

    @Value("${spring.rabbitmq.pending-orders-queue}")
    private String pendingOrdersQueue;

    @RabbitListener(queues = "pending-orders-queue")
    public void receiveOrderId(String exchangeOrderId) throws ExchangeException, OrderException {
        String cleanedExchangeOrderId = exchangeOrderId.replaceAll("^\"|\"$", "");

        orderService.updateOrderStatus(cleanedExchangeOrderId);
    }
}
