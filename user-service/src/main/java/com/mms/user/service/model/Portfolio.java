package com.mms.user.service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Portfolio extends BaseEntity {
    @Id
    @GeneratedValue
    private Integer id;
    @Getter
    private String portfolioName;
    private PortfolioType portfolioType;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User owner;
    private  Status status;

    public enum PortfolioType {
        DEFAULT, CUSTOM
    }

    public enum Status {
        ACTIVE, DISABLED
    }
}
