package com.mms.market_data_service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "spring.application")
@Getter
@Setter
public class AppProperties {
    private List<String> exchanges;
    private List<String> stocks;

    @PostConstruct
    void onStartup() {
        System.out.println("Exchanges: " + exchanges);
        System.out.println("Stocks: " + stocks);
    }
}
