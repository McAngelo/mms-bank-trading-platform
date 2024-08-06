package com.mms.order.manager.models;

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
    private Portfolio.PortfolioType portfolioType;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User owner;

    //private boolean isDefault;
    private Portfolio.Status status;

    public enum PortfolioType {
        DEFAULT, CUSTOM
    }

    public enum Status {
        ACTIVE, DISABLED
    }
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
