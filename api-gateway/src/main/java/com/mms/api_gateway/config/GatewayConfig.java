package com.mms.api_gateway.config;

import com.mms.api_gateway.auth.AuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {
    private final AuthFilter authFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth_route", r -> r
                        .path("/api/v1/auth/**")
                        .uri("lb://user-service")
                )
                .route("order_route", r -> r
                        .host("api/v1/order/**")
                        .filters(f -> f.filters(authFilter))
                        .uri("lb://order-service")
                )
                .route("report_route", r -> r
                        .host("api/v1/report/**")
                        .uri("lb://report-service")
                ).build();
    }
}
