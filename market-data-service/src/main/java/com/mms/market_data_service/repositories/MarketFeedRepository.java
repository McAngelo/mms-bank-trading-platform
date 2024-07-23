package com.mms.market_data_service.repositories;

import com.mms.market_data_service.dtos.OrderDto;
import com.mms.market_data_service.models.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketFeedRepository extends CrudRepository<Order, String> {
    List<Order> findByOrderID(String orderID);
    List<Order> findByProduct(String product);
    List<Order> findByExchange(String exchange);
}