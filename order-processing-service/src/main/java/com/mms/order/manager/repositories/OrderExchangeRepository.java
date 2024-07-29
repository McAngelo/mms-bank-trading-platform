package com.mms.order.manager.repositories;

import com.mms.order.manager.models.OrderExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderExchangeRepository extends JpaRepository<OrderExchange, Long> {
}
