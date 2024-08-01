package com.mms.order.manager.services.impl;

import com.mms.order.manager.dtos.internal.CreateExchangeOrderDto;
import com.mms.order.manager.exceptions.ExchangeException;
import com.mms.order.manager.models.Exchange;
import com.mms.order.manager.models.Order;
import com.mms.order.manager.models.SplitOrder;
import com.mms.order.manager.repositories.ExchangeRepository;
import com.mms.order.manager.repositories.SplitOrderRepositories;
import com.mms.order.manager.utils.ExchangeExecutor;
import com.mms.order.manager.utils.converters.OrderConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NonSplitOrderExecutionService {
    private final SplitOrderRepositories splitOrderRepositories;
    private final ExchangeExecutor exchangeExecutor;
    private final ExchangeRepository exchangeRepository;
    private final OrderConvertor orderConvertor;

    public void executeOrder(Order order, String exchangeSlug) throws ExchangeException {
        CreateExchangeOrderDto exchangeOrderDto = orderConvertor.convert(order);
        Exchange exchange = getExchange(exchangeSlug);

        String exchangeOrderId = exchangeExecutor.executeOrder(exchangeOrderDto, exchange);

        splitOrderRepositories.save(SplitOrder.builder()
                .order(order)
                .exchange(exchange)
                .quantity(exchangeOrderDto.quantity())
                .exchangeOrderId(exchangeOrderId)
                .build()
        );
    }

    private Exchange getExchange(String exchangeSlug) throws ExchangeException {
        Optional<Exchange> optionalExchange = exchangeRepository.findBySlug(exchangeSlug);

        if (optionalExchange.isEmpty()) {
            throw new ExchangeException("Invalid exchange slug");
        }

        return optionalExchange.get();
    }
}
