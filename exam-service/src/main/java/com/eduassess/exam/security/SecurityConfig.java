package com.eduassess.exam.security;

import com.eduassess.security.JwtAuthenticationFilter;
import com.eduassess.security.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration @EnableMethodSecurity
public class SecurityConfig {
    @Bean JwtService jwtService(@Value("${security.jwt.secret}") String secret,@Value("${security.jwt.expiration-ms}") long exp){
        return new JwtService(secret,exp);
    }
    @Bean SecurityFilterChain chain(HttpSecurity http,JwtService jwt) throws Exception{
        http.csrf(c->c.disable()).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(a->a.requestMatchers("/actuator/health").permitAll().anyRequest().authenticated())
            .addFilterBefore(new JwtAuthenticationFilter(jwt), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
