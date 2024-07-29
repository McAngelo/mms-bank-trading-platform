package com.mms.order.manager.utils.template;

import com.mms.order.manager.dtos.requests.CreateOrderDto;
import com.mms.order.manager.exceptions.MarketDataException;
import com.mms.order.manager.services.interfaces.MarketDataService;
import com.mms.order.manager.services.interfaces.PortfolioService;
import com.mms.order.manager.services.interfaces.WalletService;


public class SingleExchangeOrderValidator extends OrderValidator {
    private final MarketDataService marketDataService;

    public SingleExchangeOrderValidator(WalletService walletService, PortfolioService portfolioService, MarketDataService marketDataService) {
        super(walletService, portfolioService);
        this.marketDataService = marketDataService;
    }

    @Override
    protected boolean validateMarketConditions(CreateOrderDto orderDto) throws MarketDataException {
        var productData = marketDataService.getProductData(orderDto.preferredExchangeSlug(), orderDto.productSlug());

       return validateSingleOrderAgainstMarketConditions(orderDto, productData);
    }
}
