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

//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "productId", insertable = false, updatable = false)
//    private Product product;
//    private long productId;

    private String ticker;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "portfolioId", insertable = false, updatable = false)
    private Portfolio portfolio;
    private long portfolioId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;
    private long userId;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<Execution> executions;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<SplitOrder> orderSplits;

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
}
