package com.mms.order.manager.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Exchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "exchange")
    private List<Execution> executions;

    private String privateKey;

    private String name;

    private String slug;

    private String baseUrl;

    private boolean isActive;
}
