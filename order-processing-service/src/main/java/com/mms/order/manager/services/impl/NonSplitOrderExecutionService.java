package com.mms.order.manager.services.impl;

import com.mms.order.manager.dtos.internal.CreateExchangeOrderDto;
import com.mms.order.manager.exceptions.ExchangeException;
import com.mms.order.manager.models.Exchange;
import com.mms.order.manager.models.Order;
import com.mms.order.manager.models.OrderSplit;
import com.mms.order.manager.repositories.ExchangeRepository;
import com.mms.order.manager.repositories.OrderSplitRepository;
import com.mms.order.manager.utils.ExchangeExecutor;
import com.mms.order.manager.utils.converters.OrderConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NonSplitOrderExecutionService {
    private final OrderSplitRepository splitOrderSplitRepository;
    private final ExchangeExecutor exchangeExecutor;
    private final ExchangeRepository exchangeRepository;
    private final OrderConvertor orderConvertor;
    private final RedisServiceImpl redisService;

    public void executeOrder(Order order, String exchangeSlug) throws ExchangeException {
        CreateExchangeOrderDto exchangeOrderDto = orderConvertor.convert(order);
        Exchange exchange = getExchange(exchangeSlug);

        String exchangeOrderId = exchangeExecutor.executeOrder(exchangeOrderDto, exchange);

        splitOrderSplitRepository.save(OrderSplit.builder()
                .order(order)
                .exchange(exchange)
                .quantity(exchangeOrderDto.quantity())
                .exchangeOrderId(exchangeOrderId)
                .build()
        );
    }

//    private void saveOrderInCache(String exchangeSlug, String exchangeOrderId) {
//        String key = String.format("%s:PENDING:ORDERS",exchangeSlug);
//        redisService.setValue(key);
//    }

    private Exchange getExchange(String exchangeSlug) throws ExchangeException {
        Optional<Exchange> optionalExchange = exchangeRepository.findBySlug(exchangeSlug);

        if (optionalExchange.isEmpty()) {
            throw new ExchangeException("Invalid exchange slug");
        }

        return optionalExchange.get();
    }
}
