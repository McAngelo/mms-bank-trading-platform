package com.mms.order.manager.utils.mappers;

import com.mms.order.manager.exceptions.OrderException;
import com.mms.order.manager.models.Portfolio;
import com.mms.order.manager.models.Product;
import com.mms.order.manager.models.User;
import com.mms.order.manager.repositories.ProductRepository;
import com.mms.order.manager.repositories.UserRepository;
import com.mms.order.manager.services.interfaces.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderIdsMapper {
    private final PortfolioService portfolioService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public User userFromId(long id) throws OrderException {
        return userRepository.findById(id)
                .orElseThrow(() -> new OrderException("User does not exist"));
    }

    public Product productFromId(long id) throws OrderException {
        return productRepository.findById(id)
                .orElseThrow(() -> new OrderException("Product does not exist"));
    }

    public Portfolio portfolioFromId(long id) throws OrderException {
        return portfolioService.findById(id)
                .orElseThrow(() -> new OrderException("Portfolio does not exist"));
    }
}
