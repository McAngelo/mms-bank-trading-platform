package com.mms.order.manager.services.impl;

import com.mms.order.manager.dtos.internal.CreateExchangeOrderDto;
import com.mms.order.manager.dtos.internal.ProductMarketData;
import com.mms.order.manager.dtos.requests.CreateOrderDto;
import com.mms.order.manager.enums.ExecutionMode;
import com.mms.order.manager.enums.OrderSide;
import com.mms.order.manager.enums.OrderType;
import com.mms.order.manager.exceptions.ExchangeException;
import com.mms.order.manager.exceptions.MarketDataException;
import com.mms.order.manager.exceptions.OrderException;
import com.mms.order.manager.models.Exchange;
import com.mms.order.manager.repositories.ExecutionRepository;
import com.mms.order.manager.repositories.OrderRepository;
import com.mms.order.manager.repositories.OrderSplitRepository;
import com.mms.order.manager.services.interfaces.MarketDataService;
import com.mms.order.manager.services.interfaces.OrderService;
import com.mms.order.manager.services.interfaces.WalletService;
import com.mms.order.manager.utils.ExchangeExecutor;
import com.mms.order.manager.utils.converters.OrderConvertor;
import com.mms.order.manager.utils.OrderValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@RunWith(SpringRunner.class)
class OrderServiceImplTest {
    @Mock
    OrderValidator orderValidator;

    @Mock
    OrderExecutionService orderExecutionService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    ExchangeExecutor exchangeExecutor;

    @Mock
    WalletService walletService;

    @Mock
    MarketDataService marketDataService;

    @Mock
    OrderSplitRepository orderSplitRepository;;

    @Mock
    ExecutionRepository executionRepository;

    @InjectMocks
    OrderConvertor orderConvertor;

    @InjectMocks
    OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        orderValidator = new OrderValidator(walletService, orderRepository, marketDataService);
        orderService = new OrderServiceImpl(orderRepository, orderValidator, orderExecutionService, orderSplitRepository, executionRepository, orderConvertor);
    }

    @Test
    void shouldSuccessfullyCreateOrder() throws ExchangeException, MarketDataException {
        // GIVEN
        var orderDto = CreateOrderDto.builder()
                .portfolioId(1)
                .userId(1)
                .ticker("IBM")
                .quantity(10)
                .executionMode(ExecutionMode.SINGLE_EXCHANGE)
                .preferredExchangeSlug("exchange-1")
                .price(new BigDecimal("5.25"))
                .side(OrderSide.BUY)
                .type(OrderType.MARKET)
                .build();

        var productMarketData = ProductMarketData.builder()
                .ticker("IBM")
                .sellLimit(5000)
                .lastTradedPrice(1.30)
                .maxPriceShift(0.13)
                .askPrice(1.32)
                .bidPrice(1.30)
                .buyLimit(10000)
                .build();

        when(walletService.getBalanceByUserId(anyLong())).thenReturn(Optional.of(new BigDecimal(10)));
        when(marketDataService.getProductData(anyString(), anyString())).thenReturn(productMarketData);
        when(exchangeExecutor.executeOrder(any(CreateExchangeOrderDto.class), any(Exchange.class))).thenReturn("exchange-order-id");
        when(orderRepository.existsByPortfolioIdAndUserIdAndTickerAndQuantityGreaterThanEqual(anyLong(), anyLong(), anyString(), anyInt())).thenReturn(true);


        assertDoesNotThrow(() -> orderService.createOrder(orderDto));
        verify(walletService).getBalanceByUserId(anyLong());
        verify(marketDataService).getProductData(anyString(), anyString());
        verify(exchangeExecutor).executeOrder(any(CreateExchangeOrderDto.class), any(Exchange.class));
        verify(orderRepository).existsByPortfolioIdAndUserIdAndTickerAndQuantityGreaterThanEqual(anyLong(), anyLong(), anyString(), anyInt());
    }

    @Test
    void shouldThrowOrderExceptionForInsufficientFundsWhenCreatingOrder() {
        // GIVEN
        var orderDto = CreateOrderDto.builder()
                .portfolioId(1)
                .userId(1)
                .ticker("IBM")
                .quantity(100)
                .executionMode(ExecutionMode.SINGLE_EXCHANGE)
                .preferredExchangeSlug("exchange-1")
                .price(new BigDecimal("5.25"))
                .side(OrderSide.BUY)
                .type(OrderType.MARKET)
                .build();

        when(walletService.getBalanceByUserId(anyLong())).thenReturn(Optional.of(new BigDecimal(10)));

        assertThrows(OrderException.class, () -> orderService.createOrder(orderDto));
        verify(walletService).getBalanceByUserId(anyLong());
    }
}