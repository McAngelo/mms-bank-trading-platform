package com.mms.order.manager.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mms.order.manager.enums.ExecutionMode;
import com.mms.order.manager.enums.OrderSide;
import com.mms.order.manager.enums.OrderStatus;
import com.mms.order.manager.enums.OrderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Slf4j
@Builder
@Table(name="market_order")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String ticker;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "portfolioId", insertable = false, updatable = false)
    private Portfolio portfolio;
    private long portfolioId;

    private long userId;

    @Enumerated(EnumType.STRING)
    private OrderSide side;

    @Enumerated(EnumType.STRING)
    private OrderType type;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    private ExecutionMode executionMode;

    private int quantity;
    private BigDecimal price;
    private LocalDateTime createdAt;
}
