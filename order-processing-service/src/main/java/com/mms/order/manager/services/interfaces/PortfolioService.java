package com.mms.order.manager.services.interfaces;

public interface PortfolioService {
    boolean createPortfolio(long userId);

    boolean userOwnsProduct(long userId, String product, int quantity);
}
