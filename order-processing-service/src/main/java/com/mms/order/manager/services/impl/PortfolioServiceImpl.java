package com.mms.order.manager.services.impl;

import com.mms.order.manager.dtos.responses.PortfolioDto;
import com.mms.order.manager.models.Portfolio;
import com.mms.order.manager.models.User;
import com.mms.order.manager.repositories.OrderRepository;
import com.mms.order.manager.repositories.PortfolioRepository;
import com.mms.order.manager.repositories.UserRepository;
import com.mms.order.manager.services.interfaces.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    public boolean createPortfolio(long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return false;
        }

        var portfolio = Portfolio.builder()
                .user(userOptional.get())
                .build();

        portfolioRepository.save(portfolio);
        return true;
    }

    public List<PortfolioDto> getPortfolios(Long userId) {
        var portfolios = portfolioRepository.findByUserId(userId);

        return portfolios.stream()
                .map(p -> new PortfolioDto(p.getId(), p.getName()))
                .toList();
    }

//    public PortfolioDto getUserPortfolio(Long portfolioId) {
//        var portfolioItems = orderRepository.findById(portfolioId);
//
//
//    }

    @Override
    public boolean userOwnsProduct(long userId, String product, int quantity) {
        return false;
    }
}
