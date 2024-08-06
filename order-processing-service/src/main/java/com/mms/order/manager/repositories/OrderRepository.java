package com.mms.order.manager.repositories;

import com.mms.order.manager.enums.OrderStatus;
import com.mms.order.manager.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByPortfolioIdAndStatus(long portfolioId, OrderStatus status);

    Page<Order> findByPortfolioId(Long portfolioId, Pageable pageable);

    boolean existsByPortfolioIdAndUserIdAndTickerAndQuantityGreaterThanEqual(
            long portfolioId,
            long userId,
            String ticker,
            int quantity);
}
