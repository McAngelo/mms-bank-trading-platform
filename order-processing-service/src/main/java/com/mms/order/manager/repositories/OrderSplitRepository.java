package com.mms.order.manager.repositories;

import com.mms.order.manager.models.OrderSplit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderSplitRepository extends JpaRepository<OrderSplit, Long> {
    List<OrderSplit> findAllByOrderId(Long orderId);

    Optional<OrderSplit> findByExchangeOrderId(String exchangeOrderId);
}
