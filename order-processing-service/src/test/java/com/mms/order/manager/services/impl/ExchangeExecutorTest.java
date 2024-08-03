package com.mms.order.manager.services.impl;

import com.mms.order.manager.dtos.internal.CreateExchangeOrderDto;
import com.mms.order.manager.enums.OrderSide;
import com.mms.order.manager.enums.OrderType;
import com.mms.order.manager.exceptions.ExchangeException;
import com.mms.order.manager.models.Exchange;
import com.mms.order.manager.repositories.ExchangeRepository;
import com.mms.order.manager.utils.ExchangeExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@RunWith(SpringRunner.class)
class ExchangeExecutorTest {
    @Mock
    ExchangeRepository exchangeRepository;

    @Mock
    RestTemplate restTemplate;

    @Mock
    RedisTemplate<String, Serializable> redisTemplate;

    @InjectMocks
    ExchangeExecutor exchangeExecutor;

    @BeforeEach
    void setUp() {
        exchangeExecutor = new ExchangeExecutor(restTemplate, redisTemplate);
    }

    @Test
    void shouldSuccessfullyExecuteOrderOnExchange() {
        // GIVEN
        var order = CreateExchangeOrderDto.builder()
                .product("GOOGL")
                .quantity(1)
                .side(OrderSide.BUY.toString())
                .type(OrderType.MARKET.toString())
                .build();

        var exchange = Exchange.builder()
                .baseUrl("http://test-exchange.com")
                .privateKey("test-key")
                .build();

        when(exchangeRepository.findBySlug(anyString())).thenReturn(Optional.of(exchange));
        when(restTemplate.postForEntity(
                anyString(),
                any(CreateExchangeOrderDto.class),
                any()
        )).thenReturn(new ResponseEntity<>("02c27916-1056-41f6-a763-5cdceccfddaf", HttpStatus.OK));

        // ASSERT
        assertDoesNotThrow(() -> exchangeExecutor.executeOrder(order, exchange));
        verify(restTemplate).postForEntity(anyString(), any(CreateExchangeOrderDto.class), any());
    }

    @Test
    void shouldThrowExchangeExceptionWhenSendingOrderToExchange() {
        // GIVEN
        var order = CreateExchangeOrderDto.builder()
                .product("GOOGL")
                .quantity(1)
                .side(OrderSide.BUY.toString())
                .type(OrderType.MARKET.toString())
                .build();

        var exchange = Exchange.builder()
                .baseUrl("http://test-exchange.com")
                .privateKey("test-key")
                .build();

        when(exchangeRepository.findBySlug(anyString())).thenReturn(Optional.of(exchange));
        when(restTemplate.postForEntity(
                anyString(),
                any(CreateExchangeOrderDto.class),
                any()
        )).thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

        // ASSERT
        assertThrows(ExchangeException.class, () -> exchangeExecutor.executeOrder(order, exchange));
        verify(restTemplate).postForEntity(anyString(), any(CreateExchangeOrderDto.class), any());
    }
}