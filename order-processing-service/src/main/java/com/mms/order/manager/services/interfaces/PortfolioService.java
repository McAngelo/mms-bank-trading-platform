package com.mms.order.manager.services.interfaces;

import com.mms.order.manager.dtos.requests.CreatePortfolioDto;
import com.mms.order.manager.exceptions.PortfolioException;
import com.mms.order.manager.models.Portfolio;

import java.util.Optional;

public interface PortfolioService {
    void createPortfolio(CreatePortfolioDto portfolioDto) throws PortfolioException;

    Optional<Portfolio> findById(long portfolioId);

    boolean userOwnsProduct(long userId, long portfolioId, String ticker, int quantity);
}
