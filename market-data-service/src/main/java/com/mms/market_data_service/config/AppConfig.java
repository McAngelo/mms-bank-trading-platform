package com.mms.market_data_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mms.market_data_service.services.interfaces.ExchangeService;
import com.mms.market_data_service.services.interfaces.RedisService;
import com.mms.market_data_service.tasks.ScheduledTasks;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public CommandLineRunner runAtStartup(
            RabbitAdmin rabbitAdmin,
            ExchangeService exchangeService,
            RedisService redisService,
            ObjectMapper objectMapper
    ) {
        return args -> {
            ScheduledTasks startupJob = new ScheduledTasks(exchangeService, redisService, objectMapper);
            startupJob.onStartup();
            rabbitAdmin.initialize();
        };
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
