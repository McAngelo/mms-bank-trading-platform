package com.mms.order.manager.utils.factories;

import com.mms.order.manager.enums.ExecutionMode;
import com.mms.order.manager.utils.template.MultiExchangeOrderValidator;
import com.mms.order.manager.utils.template.OrderValidator;
import com.mms.order.manager.utils.template.SingleExchangeOrderValidator;
import com.mms.order.manager.services.interfaces.MarketDataService;
import com.mms.order.manager.services.interfaces.PortfolioService;
import com.mms.order.manager.services.interfaces.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderValidatorFactoryImpl implements OrderValidatorFactory {
    private final PortfolioService portfolioService;
    private final WalletService walletService;
    private final MarketDataService marketDataService;

    @Override
    public OrderValidator getOrderValidator(ExecutionMode executionMode) {
        return switch (executionMode) {
            case INSTANT -> new SingleExchangeOrderValidator(walletService, portfolioService, marketDataService);
            case BEST_PRICE -> new MultiExchangeOrderValidator(walletService, portfolioService, marketDataService);
            default -> throw new IllegalArgumentException("Unsupported execution mode: " + executionMode);
        };
    }
}
