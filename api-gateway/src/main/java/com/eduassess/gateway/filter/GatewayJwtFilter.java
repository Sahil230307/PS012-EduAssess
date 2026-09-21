package com.eduassess.gateway.filter;

import com.eduassess.security.JwtService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;

import reactor.core.publisher.Mono;

@Component
public class GatewayJwtFilter implements GlobalFilter {

    private final JwtService jwtService;

    public GatewayJwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {

        String path = exchange.getRequest()
                .getURI()
                .getPath();

        /*
         * Public endpoints.
         * These requests must NOT require a JWT.
         */
        if (path.equals("/auth/login")
                || path.equals("/auth/register")
                || path.equals("/actuator/health")
                || path.equals("/")) {

            return chain.filter(exchange);
        }

        /*
         * Get Authorization header.
         */
        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        /*
         * JWT is required for protected endpoints.
         */
        if (authHeader == null
                || !authHeader.startsWith("Bearer ")) {

            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        /*
         * Validate JWT.
         */
        try {

            if (!jwtService.isValid(token)) {

                exchange.getResponse()
                        .setStatusCode(HttpStatus.UNAUTHORIZED);

                return exchange.getResponse().setComplete();
            }

        } catch (Exception e) {

            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse().setComplete();
        }

        /*
         * JWT is valid.
         */
        return chain.filter(exchange);
    }
}