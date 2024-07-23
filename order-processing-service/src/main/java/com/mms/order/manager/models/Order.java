package com.mms.order.manager.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mms.order.manager.dtos.requests.CreateOrderDto;
import com.mms.order.manager.enums.OrderSide;
import com.mms.order.manager.enums.OrderStatus;
import com.mms.order.manager.enums.OrderType;
import com.mms.order.manager.services.interfaces.MarketDataService;
import com.mms.order.manager.services.interfaces.PortfolioService;
import com.mms.order.manager.services.interfaces.WalletService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Builder
@Table(name="TradeOrder")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "productId", insertable = false, updatable = false)
    private Product product;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "portfolioId", insertable = false, updatable = false)
    private Portfolio portfolio;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;

    @OneToMany(mappedBy = "order")
    private List<Execution> executions;

    @Enumerated(EnumType.STRING)
    private OrderSide side;

    @Enumerated(EnumType.STRING)
    private OrderType type;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String exchangeOrderId;

    private int quantity;
    private BigDecimal price;

    public boolean validateOrder(WalletService walletService, PortfolioService portfolioService, MarketDataService marketDataService) {
        var optionalWalletBalance = walletService.getBalanceByUserId(user.getId());

        if (optionalWalletBalance.isEmpty()) {
            return false;
        }

        var walletBalance = optionalWalletBalance.get();

        // Validate sufficient funds for a buy order
        if (side == OrderSide.BUY && walletBalance.compareTo(price) < 0) {
            return false;
        }

        // Validate ownership of product for a sell order
        var doesUserOwnProduct = portfolioService.userOwnsProduct(user.getId(), product.getSymbol(), quantity);
        if (side == OrderSide.SELL && !doesUserOwnProduct) {
            return false;
        }

        var currentPrice = marketDataService.getLatestProductPrice(product.getSymbol());
        var priceDifference = currentPrice.subtract(price).abs();
        var acceptableDifference = new BigDecimal(10);

        // Check if the requested order price is reasonable
        return priceDifference.compareTo(acceptableDifference) <= 0;
    }
}
