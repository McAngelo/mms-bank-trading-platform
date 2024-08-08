package com.mms.order.manager.models;

import com.mms.order.manager.enums.PortfolioStatus;
import com.mms.order.manager.enums.PortfolioType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String portfolioName;
    private PortfolioType portfolioType;

    private long userId;
    private boolean isActive;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
