//package com.mms.order.manager.services.impl;
//
//import com.mms.order.manager.dtos.requests.CreateOrderDto;
//import com.mms.order.manager.dtos.responses.GetOrderDto;
//import com.mms.order.manager.enums.OrderStatus;
//import com.mms.order.manager.enums.OrderType;
//import com.mms.order.manager.helpers.converters.OrderConverter;
//import com.mms.order.manager.models.Exchange;
//import com.mms.order.manager.models.Order;
//import com.mms.order.manager.services.interfaces.*;
//import com.mms.order.manager.repositories.OrderRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class OrderServiceImpl implements OrderService {
//    private final OrderConverter orderConverter;
//    private final OrderRepository orderRepository;
//    private final MarketDataService marketDataService;
//    private final WalletService walletService;
//    private final PortfolioService portfolioService;
//    private final TradeStrategyService strategyService;
//
//    @Override
//    public boolean createOrder(CreateOrderDto orderDto) {
//        var order = orderConverter.convert(orderDto);
//
//        var isValidOrder = order.validateOrder(walletService, portfolioService, marketDataService);
//
//        if (!isValidOrder) {
//            return false;
//        }
//
//        orderRepository.save(order);
//        return true;
//    }
//
//    @Override
//    public Optional<GetOrderDto> getOrder(long orderId) {
//        Optional<Order> optionalOrder = orderRepository.findById(orderId);
//
//        if (optionalOrder.isEmpty()) {
//            return Optional.empty();
//        }
//
//        var order = optionalOrder.get();
//
//        if (order.getStatus() == OrderStatus.PENDING) {
//            updateOrderStatus(order.getExchangeOrderId());
//        }
//
//        var orderDto = orderConverter.convert(order);
//        return Optional.of(orderDto);
//    }
//
//    @Override
//    public boolean cancelOrder(long orderId) {
//        return false;
//    }
//
//    private void updateOrderStatus(String exchangeOrderStatus) {
//
//    }
//}
