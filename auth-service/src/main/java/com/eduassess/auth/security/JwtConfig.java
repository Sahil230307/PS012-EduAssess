package com.eduassess.auth.security;

import com.eduassess.security.JwtAuthenticationFilter;
import com.eduassess.security.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class JwtConfig {
    @Bean
    JwtService jwtService(@Value("${security.jwt.secret}") String secret,
                          @Value("${security.jwt.expiration-ms}") long expirationMs) {
        return new JwtService(secret, expirationMs);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtService jwtService) throws Exception {
        var filter = new JwtAuthenticationFilter(jwtService);
        http.csrf(csrf -> csrf.disable())
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(a -> a.requestMatchers("/auth/login", "/auth/register", "/actuator/health").permitAll()
                    .anyRequest().authenticated())
            .addFilterAt(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
