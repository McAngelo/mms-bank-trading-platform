package com.mms.order.manager.services.impl;

import com.mms.order.manager.dtos.requests.CreatePortfolioDto;
import com.mms.order.manager.dtos.responses.PortfolioDto;
import com.mms.order.manager.exceptions.PortfolioException;
import com.mms.order.manager.models.Portfolio;
import com.mms.order.manager.models.User;
import com.mms.order.manager.repositories.OrderRepository;
import com.mms.order.manager.repositories.PortfolioRepository;
import com.mms.order.manager.repositories.UserRepository;
import com.mms.order.manager.services.interfaces.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;

    @Override
    public void createPortfolio(CreatePortfolioDto portfolioDto) throws PortfolioException {
        Optional<User> userOptional = userRepository.findById(portfolioDto.ownerId());

        if (userOptional.isEmpty()) {
            throw new PortfolioException("User does not exist, could not create portfolio");
        }

        var portfolio = Portfolio.builder()
                .owner(userOptional.get())
                .portfolioName(portfolioDto.name())
                .createdAt(LocalDateTime.now())
                .build();

        portfolioRepository.save(portfolio);
    }

    @Override
    public Optional<Portfolio> findById(long portfolioId) {
        return portfolioRepository.findById(portfolioId);
    }

    public List<PortfolioDto> getPortfolios(Long userId) {
        var portfolios = portfolioRepository.findByOwnerId(userId);

        return portfolios.stream()
                .map(p -> new PortfolioDto(p.getId(), p.getPortfolioName()))
                .toList();
    }

    @Override
    public boolean userOwnsProduct(long userId, long portfolioId, String ticker, int quantity) {
        return false;
    }
}
