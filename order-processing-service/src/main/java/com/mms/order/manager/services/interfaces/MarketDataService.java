package com.mms.order.manager.services.interfaces;

import java.math.BigDecimal;

public interface MarketDataService {
    BigDecimal getLatestProductPrice(String product);
}
