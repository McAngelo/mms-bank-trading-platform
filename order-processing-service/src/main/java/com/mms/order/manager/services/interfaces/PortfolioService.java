package com.mms.order.manager.services.interfaces;

import com.mms.order.manager.dtos.requests.CreatePortfolioDto;
import com.mms.order.manager.dtos.responses.PortfolioDto;
import com.mms.order.manager.exceptions.PortfolioException;
import com.mms.order.manager.models.Portfolio;

import java.util.List;
import java.util.Optional;

public interface PortfolioService {
    void createPortfolio(CreatePortfolioDto portfolioDto) throws PortfolioException;

    List<PortfolioDto> getPortfoliosByUserId(long userId);

    Optional<Portfolio> findById(long portfolioId);
}
