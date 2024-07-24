/*
package com.mms.order.manager.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@Table(name="Execution")
@AllArgsConstructor
@NoArgsConstructor
public class Execution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "orderId", insertable = false, updatable = false)
    private Order order;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "exchangeId", insertable = false, updatable = false)
    private Exchange exchange;

    private BigDecimal price;
    private int quantity;
}
*/
