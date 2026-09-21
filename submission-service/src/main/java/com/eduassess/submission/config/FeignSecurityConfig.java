package com.eduassess.submission.config;

import com.eduassess.security.JwtService;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignSecurityConfig {
 @Bean
 RequestInterceptor serviceJwtInterceptor(
     @Value("${security.jwt.secret}") String secret,
     @Value("${security.jwt.expiration-ms}") long exp) {
   JwtService jwt=new JwtService(secret,exp);
   return template -> template.header("Authorization","Bearer "+jwt.generateToken("SERVICE","submission-service@eduassess.com","SERVICE"));
 }
}
