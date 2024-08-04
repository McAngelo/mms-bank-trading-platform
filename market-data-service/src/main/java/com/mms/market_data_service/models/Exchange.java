package com.mms.market_data_service.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Exchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String privateKey;

    private String name;

    private String slug;

    private String baseUrl;

    private boolean isSubscribed;

    private boolean isActive;
}
