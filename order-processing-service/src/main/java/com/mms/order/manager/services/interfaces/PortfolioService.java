package com.mms.order.manager.services.interfaces;

import com.mms.order.manager.models.Portfolio;

import java.util.Optional;

public interface PortfolioService {
    boolean createPortfolio(long userId);

    Optional<Portfolio> findById(long portfolioId);

    boolean userOwnsProduct(long userId, String productSlug, int quantity);
}
