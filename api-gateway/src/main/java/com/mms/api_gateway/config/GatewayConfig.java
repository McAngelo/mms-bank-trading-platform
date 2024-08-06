package com.mms.api_gateway.config;

import com.mms.api_gateway.auth.AuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {
    private final AuthFilter authFilter;

//    @Bean
//    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(r -> r.path("/api/v1/auth/**")
//                        .uri("lb://user-service")
//                )
//                .route(r -> r.path("api/v1/order/**")
////                        .filters(f -> f.filters(authFilter))
//                        .uri("lb://order-manager")
//                )
//                .route(r -> r.path("api/v1/report/**")
//                        .uri("lb://report-service")
//                ).build();
//    }
//
//    @Bean
//    public RouteLocator swagggerRouteLocator(RouteLocatorBuilder builder) {
//        return builder
//                .routes()
//                .route(r -> r.path("/service/user/api/v1/api-docs", "/service/user/swagger-ui.html")
//                        .and()
//                        .method(HttpMethod.GET)
//                        .filters(f -> f.rewritePath("/service/user(?<segment>/.*)", "${segment}"))
//                        .uri("lb://user.service"))
//                .route(r -> r.path("/service/order/api/v1/api-docs", "/service/order/swagger-ui.html")
//                        .and()
//                        .method(HttpMethod.GET)
//                        .filters(f -> f.rewritePath("/service/order(?<segment>/.*)", "${segment}"))
//                        .uri("lb://order.manager"))
//                .build();
//    }
}
