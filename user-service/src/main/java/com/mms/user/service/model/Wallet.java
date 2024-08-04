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
public class Wallet extends BaseEntity {
    @Id
    @GeneratedValue
    private Integer id;
    @Getter
    private long balance;
    private Status status;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User owner;

    public enum Status {
        ACTIVE, DISABLED
    }

}
