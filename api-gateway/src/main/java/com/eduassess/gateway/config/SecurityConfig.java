package com.eduassess.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http) {

        return http

                // JWT-based REST API does not use CSRF tokens
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                // Authorization rules
                .authorizeExchange(exchange -> exchange

                        // Public endpoints
                        .pathMatchers(
                                "/",
                                "/auth/login",
                                "/auth/register",
                                "/actuator/health"
                        ).permitAll()

                        // Everything else requires authentication
                        .anyExchange().authenticated()
                )

                .build();
    }
}