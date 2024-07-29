package com.mms.order.manager.utils.template;

import com.mms.order.manager.dtos.internal.ProductData;
import com.mms.order.manager.dtos.requests.CreateOrderDto;
import com.mms.order.manager.enums.OrderSide;
import com.mms.order.manager.exceptions.MarketDataException;
import com.mms.order.manager.exceptions.OrderException;
import com.mms.order.manager.services.interfaces.PortfolioService;
import com.mms.order.manager.services.interfaces.WalletService;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public abstract class OrderValidator {
    private final WalletService walletService;
    private final PortfolioService portfolioService;

    public final void validateOrder(CreateOrderDto orderDto) throws MarketDataException, OrderException {
        if (!validateFunds(orderDto)) {
            throw new OrderException("Insufficient funds");
        }
        if (!validateOwnership(orderDto)) {
            throw new OrderException("Cannot place for order for product not owned");
        }
        if (!validateMarketConditions(orderDto)) {
            throw new OrderException("Order price/quantity is too high or low");
        }
    }

    protected boolean validateFunds(CreateOrderDto orderDto) {
        var optionalWalletBalance = walletService.getBalanceByUserId(orderDto.userId());

        if (optionalWalletBalance.isEmpty()) {
            return false;
        }

        var walletBalance = optionalWalletBalance.get();
        return orderDto.side() == OrderSide.BUY && walletBalance.compareTo(orderDto.price()) >= 0;
    }

    protected boolean validateOwnership(CreateOrderDto orderDto) {
        return orderDto.side() != OrderSide.SELL && portfolioService.userOwnsProduct(
                orderDto.userId(),
                orderDto.productSlug(),
                orderDto.quantity()
        );
    }

    protected boolean validateSingleOrderAgainstMarketConditions(CreateOrderDto orderDto, ProductData productData) throws MarketDataException {
        var side = orderDto.side();
        var quantity = orderDto.quantity();
        var price =  orderDto.price();

        if (OrderSide.BUY == side && quantity > productData.buyLimit()) {
            return false;
        }

        if (OrderSide.SELL == side && quantity > productData.sellLimit()) {
            return false;
        }

        var maxPriceShiftBD = BigDecimal.valueOf(productData.maxPriceShift());
        var lastTradedPriceBD = BigDecimal.valueOf(productData.lastTradedPrice());

        var acceptableMinPrice = lastTradedPriceBD.subtract(maxPriceShiftBD);
        var acceptableMaxPrice = lastTradedPriceBD.add(maxPriceShiftBD);

        return price.compareTo(acceptableMinPrice) >= 0 || price.compareTo(acceptableMaxPrice) <= 0;
    }

    protected abstract boolean validateMarketConditions(CreateOrderDto orderDto) throws MarketDataException;
}
