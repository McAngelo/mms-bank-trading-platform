package com.mms.order.manager.services.impl;

import com.mms.order.manager.dtos.internal.CreateExchangeOrderDto;
import com.mms.order.manager.enums.OrderSide;
import com.mms.order.manager.enums.OrderType;
import com.mms.order.manager.exceptions.ExchangeException;
import com.mms.order.manager.models.Exchange;
import com.mms.order.manager.models.Order;
import com.mms.order.manager.models.OrderExchange;
import com.mms.order.manager.models.Product;
import com.mms.order.manager.repositories.ExchangeRepository;
import com.mms.order.manager.repositories.OrderExchangeRepository;
import com.mms.order.manager.repositories.projections.ExchangeView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

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
    OrderExchangeRepository orderExchangeRepository;

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    ExchangeExecutor exchangeExecutor;

    @BeforeEach
    void setUp() {
        exchangeExecutor = new ExchangeExecutor(restTemplate, exchangeRepository, orderExchangeRepository);
    }

    @Test
    void shouldSuccessfullyExecuteOrderOnExchange() {
        // GIVEN
        var order = Order.builder()
                .product(Product.builder().symbol("GOOGL").build())
                .quantity(1)
                .side(OrderSide.BUY)
                .type(OrderType.MARKET)
                .build();

        var exchangeView = mock(Exchange.class);

        when(exchangeRepository.findBySlug(anyString())).thenReturn(Optional.of(exchangeView));
        when(exchangeView.getBaseUrl()).thenReturn("http://example.com");
        when(exchangeView.getPrivateKey()).thenReturn("private-key");
        when(orderExchangeRepository.save(any(OrderExchange.class))).thenReturn(mock(OrderExchange.class));

        when(restTemplate.postForEntity(
                anyString(),
                any(CreateExchangeOrderDto.class),
                any()
        )).thenReturn(new ResponseEntity<>("02c27916-1056-41f6-a763-5cdceccfddaf", HttpStatus.OK));

        // ASSERT
        assertDoesNotThrow(() -> exchangeExecutor.executeOrder(order, "exchangeSlug", 1.0));

        verify(exchangeRepository).findBySlug(anyString());
        verify(orderExchangeRepository).save(any(OrderExchange.class));
        verify(restTemplate).postForEntity(anyString(), any(CreateExchangeOrderDto.class), any());
    }

    @Test
    void shouldThrowExchangeExceptionWhenExecutingAnOrder() {
        // GIVEN
        var order = Order.builder()
                .product(Product.builder().symbol("GOOGL").build())
                .quantity(1)
                .side(OrderSide.BUY)
                .type(OrderType.MARKET)
                .build();

        when(exchangeRepository.findBySlug(anyString())).thenReturn(Optional.empty());

        // ASSERT
        assertThrows(ExchangeException.class, () -> exchangeExecutor.executeOrder(order, "exchange-slug", 1.0));
        verify(exchangeRepository).findBySlug(anyString());
    }

    @Test
    void shouldThrowExchangeExceptionWhenSendingOrderToExchange() {
        // GIVEN
        var order = Order.builder()
                .product(Product.builder().symbol("GOOGL").build())
                .quantity(1)
                .side(OrderSide.BUY)
                .type(OrderType.MARKET)
                .build();

        var exchangeView = mock(Exchange.class);

        when(exchangeRepository.findBySlug(anyString())).thenReturn(Optional.of(exchangeView));
        when(exchangeView.getBaseUrl()).thenReturn("http://example.com");
        when(exchangeView.getPrivateKey()).thenReturn("private-key");
        when(orderExchangeRepository.save(any(OrderExchange.class))).thenReturn(mock(OrderExchange.class));
        when(restTemplate.postForEntity(
                anyString(),
                any(CreateExchangeOrderDto.class),
                any()
        )).thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

        // ASSERT
        assertThrows(ExchangeException.class, () -> exchangeExecutor.executeOrder(order, "exchange-slug", 1.0));
        verify(exchangeRepository).findBySlug(anyString());
        verify(restTemplate).postForEntity(anyString(), any(CreateExchangeOrderDto.class), any());
    }
}