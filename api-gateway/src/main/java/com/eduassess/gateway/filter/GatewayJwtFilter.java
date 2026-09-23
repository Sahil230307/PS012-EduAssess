package com.eduassess.gateway.filter;

import com.eduassess.security.JwtService;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Component;

import org.springframework.web.server.ServerWebExchange;

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

        HttpMethod method = exchange.getRequest()
                .getMethod();

        /*
         * Allow CORS preflight requests.
         */
        if (method == HttpMethod.OPTIONS) {
            return chain.filter(exchange);
        }

        /*
         * Public endpoints.
         */
        if (path.equals("/")
                || path.equals("/auth/login")
                || path.equals("/auth/register")
                || path.equals("/actuator/health")) {

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

            return unauthorized(exchange);
        }

        String token = authHeader.substring(7).trim();

        if (token.isEmpty()) {
            return unauthorized(exchange);
        }

        /*
         * Validate JWT.
         */
        try {

            if (!jwtService.isValid(token)) {
                return unauthorized(exchange);
            }

        } catch (Exception e) {
            return unauthorized(exchange);
        }

        /*
         * JWT is valid.
         */
        return chain.filter(exchange);
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {

        exchange.getResponse()
                .setStatusCode(HttpStatus.UNAUTHORIZED);

        return exchange.getResponse().setComplete();
    }
}