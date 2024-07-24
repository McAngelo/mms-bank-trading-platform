package com.mms.market_data_service.config;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class DotenvConfig {
    @Bean
    public Dotenv dotenv() {
        return  Dotenv.configure().load();
    }
}
