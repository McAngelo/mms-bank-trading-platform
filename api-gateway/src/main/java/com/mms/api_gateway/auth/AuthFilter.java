package com.mms.api_gateway.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RefreshScope
@RequiredArgsConstructor
public class AuthFilter implements GatewayFilter {
    RouteValidator routeValidator;
    JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if(routeValidator.isSecured.test(request)) {

            if(this.hasNoBearerToken(request)) {
                return this.onError(exchange,"Authorization token missing", HttpStatus.UNAUTHORIZED);
            }

            String token = request.getHeaders().get("Authorization").toString().split(" ")[1];

            if (jwtUtil.isInvalid(token)) {
                return this.onError(exchange,"Auth header invalid",HttpStatus.UNAUTHORIZED);
            }
        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private boolean hasNoBearerToken(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").getFirst().isEmpty();
    }
}
