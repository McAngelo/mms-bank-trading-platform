package com.mms.order.manager.utils.template;

import com.mms.order.manager.dtos.requests.CreateOrderDto;
import com.mms.order.manager.exceptions.MarketDataException;
import com.mms.order.manager.services.interfaces.MarketDataService;
import com.mms.order.manager.services.interfaces.PortfolioService;
import com.mms.order.manager.services.interfaces.WalletService;
import org.springframework.stereotype.Component;


@Component
public class MultiExchangeOrderValidator extends OrderValidator {
    private final MarketDataService marketDataService;

    public MultiExchangeOrderValidator(WalletService walletService, PortfolioService portfolioService, MarketDataService marketDataService) {
        super(walletService, portfolioService);
        this.marketDataService = marketDataService;
    }


    @Override
    protected boolean validateMarketConditions(CreateOrderDto orderDto) throws MarketDataException {
        var productsDataMap = marketDataService.getProductDataFromAllExchanges(orderDto.productSlug());

        for (var productData : productsDataMap.values()) {
            var isValid = validateSingleOrderAgainstMarketConditions(orderDto, productData);

            if (!isValid) {
                return false;
            }
        }

        return true;
    }
}
