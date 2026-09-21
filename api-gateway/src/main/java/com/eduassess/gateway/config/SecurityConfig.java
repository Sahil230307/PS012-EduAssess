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
                // JWT REST API does not use CSRF
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                // Disable browser/basic authentication
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)

                // Disable form login
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)

                /*
                 * GatewayJwtFilter is responsible for checking
                 * whether a JWT exists and is valid.
                 *
                 * The actual user authorization is handled
                 * by the downstream microservices.
                 */
                .authorizeExchange(exchange -> exchange
                        .anyExchange().permitAll()
                )

                .build();
    }
}