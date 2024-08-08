package com.mms.order.manager.services.impl;

import com.mms.order.manager.dtos.requests.CreatePortfolioDto;
import com.mms.order.manager.dtos.responses.PortfolioDto;
import com.mms.order.manager.enums.PortfolioType;
import com.mms.order.manager.exceptions.PortfolioException;
import com.mms.order.manager.models.Portfolio;
import com.mms.order.manager.repositories.PortfolioRepository;
import com.mms.order.manager.services.interfaces.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {
    private final PortfolioRepository portfolioRepository;

    @Override
    public void createPortfolio(CreatePortfolioDto portfolioDto) {
        List<Portfolio> portfolioList = portfolioRepository.findByUserId(portfolioDto.userId());

        Portfolio.PortfolioBuilder portfolioBuilder = Portfolio.builder();

        if (portfolioList.isEmpty()) {
            portfolioBuilder.portfolioType(PortfolioType.DEFAULT);
        }

        portfolioRepository.save(portfolioBuilder
                .userId(portfolioDto.userId())
                .portfolioName(portfolioDto.name())
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build()
        );
    }

    @Override
    public List<PortfolioDto> getPortfoliosByUserId(long userId) {
        List<Portfolio> portfolioList = portfolioRepository.findByUserId(userId);


        return portfolioList.stream()
                .map(p -> new PortfolioDto(p.getId(), p.getPortfolioName()))
                .toList();
    }

    @Override
    public Optional<Portfolio> findById(long portfolioId) {
        return portfolioRepository.findById(portfolioId);
    }

    public List<PortfolioDto> getPortfolios(Long userId) {
        var portfolios = portfolioRepository.findByUserId(userId);

        return portfolios.stream()
                .map(p -> new PortfolioDto(p.getId(), p.getPortfolioName()))
                .toList();
    }
}
