package com.mms.order.manager.utils;

import com.mms.order.manager.dtos.internal.ProductMarketData;
import com.mms.order.manager.dtos.requests.CreateOrderDto;
import com.mms.order.manager.enums.ExecutionMode;
import com.mms.order.manager.enums.OrderSide;
import com.mms.order.manager.enums.OrderType;
import com.mms.order.manager.exceptions.MarketDataException;
import com.mms.order.manager.exceptions.OrderException;
import com.mms.order.manager.exceptions.UserException;
import com.mms.order.manager.repositories.OrderRepository;
import com.mms.order.manager.repositories.PortfolioRepository;
import com.mms.order.manager.services.interfaces.MarketDataService;
import com.mms.order.manager.services.interfaces.PortfolioService;
import com.mms.order.manager.services.interfaces.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderValidator {
    private final WalletService walletService;
    private final OrderRepository orderRepository;
    private final MarketDataService marketDataService;

    public final void validateOrder(CreateOrderDto orderDto) throws MarketDataException, OrderException {
        validateFunds(orderDto);
        validateOwnership(orderDto);
        validateMarketConditions(orderDto);
    }

    private void validateFunds(CreateOrderDto orderDto) throws OrderException, MarketDataException {
        BigDecimal walletBalance = walletService.getBalanceByOwnerId(orderDto.userId())
                .orElseThrow(() -> new OrderException("User wallet not found"));

        ProductMarketData productData = marketDataService.getProductData(orderDto.preferredExchangeSlug(), orderDto.ticker());

        if (orderDto.side() == OrderSide.BUY && walletBalance.compareTo(BigDecimal.valueOf(productData.lastTradedPrice())) < 0) {
            throw new OrderException("Insufficient funds");
        }
    }

    private void validateOwnership(CreateOrderDto orderDto) throws OrderException {
        if (OrderSide.SELL == orderDto.side()) {
            boolean ownsProduct = orderRepository.existsByPortfolioIdAndUserIdAndTickerAndQuantityGreaterThanEqual(orderDto.userId(), orderDto.portfolioId(), orderDto.ticker(), orderDto.quantity());

            if (!ownsProduct) {
                throw new OrderException("Cannot place order for product not owned");
            }
        }
    }

    private void validateMarketConditions(CreateOrderDto orderDto) throws MarketDataException, OrderException {
        if (ExecutionMode.SINGLE_EXCHANGE == orderDto.executionMode()) {
            validateSingleExchange(orderDto);
        } else {
            validateMultipleExchanges(orderDto);
        }
    }

    private void validateSingleExchange(CreateOrderDto orderDto) throws MarketDataException, OrderException {
        ProductMarketData productData = marketDataService.getProductData(orderDto.preferredExchangeSlug(), orderDto.ticker());

        if (OrderType.MARKET == orderDto.type() ) {
            if (validateQuantity(orderDto, productData)) {
                return;
            }
            throw new OrderException("Order is invalid due to quantity constraints");
        }

        if (!validatePrice(orderDto, productData)) {
            throw new OrderException("Order is invalid due to price constraints");
        }
    }

    private void validateMultipleExchanges(CreateOrderDto orderDto) throws OrderException {
        Map<String, ProductMarketData> productsDataMap = marketDataService.getProductDataFromAllExchanges(orderDto.ticker());

        boolean allValidQuantities = productsDataMap.values().stream().allMatch(pd -> validateQuantity(orderDto, pd));

        if (!allValidQuantities) {
            throw new OrderException("Order is invalid due to quantity constraints on one or more exchanges");
        }

        if (OrderType.MARKET == orderDto.type()) {
            return;
        }

        boolean allValidPrice = productsDataMap.values().stream().allMatch(pd -> validatePrice(orderDto, pd));

        if (!allValidPrice) {
            throw new OrderException("Order is invalid due to price constraints on one or more exchanges");
        }
    }


    private boolean validateQuantity(CreateOrderDto orderDto, ProductMarketData productMarketData) {
        if (OrderSide.BUY == orderDto.side()) {
            return orderDto.quantity() > 0 && orderDto.quantity() <= productMarketData.buyLimit();
        }

        return orderDto.quantity() > 0 && orderDto.quantity() <= productMarketData.sellLimit();
    }

    private boolean validatePrice(CreateOrderDto orderDto, ProductMarketData productMarketData) {
        BigDecimal maxPriceShiftBD = BigDecimal.valueOf(productMarketData.maxPriceShift());
        BigDecimal lastTradedPriceBD = BigDecimal.valueOf(productMarketData.lastTradedPrice());

        BigDecimal acceptableMinPrice = lastTradedPriceBD.subtract(maxPriceShiftBD);
        BigDecimal acceptableMaxPrice = lastTradedPriceBD.add(maxPriceShiftBD);

        return orderDto.price().compareTo(acceptableMinPrice) >= 0 && orderDto.price().compareTo(acceptableMaxPrice) <= 0;
    }
}
