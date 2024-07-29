package com.mms.market_data_service.config;

import com.mms.market_data_service.services.interfaces.ExchangeService;
import com.mms.market_data_service.tasks.ScheduledTasks;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public CommandLineRunner runAtStartup(ExchangeService exchangeService, RedisTemplate<String, String> redisTemplate) {
        return args -> {
            var startupJob = new ScheduledTasks(exchangeService, redisTemplate);
            startupJob.getProductsDataFromExchange1(); // Run the job at startup
        };
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
