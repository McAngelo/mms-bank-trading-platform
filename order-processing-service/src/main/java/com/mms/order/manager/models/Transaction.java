/*
package com.mms.order.manager.models;

import com.mms.order.manager.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Builder
@Table(name="Transaction")
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "walletId", insertable = false, updatable = false)
    private Wallet wallet;

    private BigDecimal amount;

    private TransactionType type;

    private LocalDate CreatedAt;

    private BigDecimal balance;
}
*/
