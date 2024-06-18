package com.fireBnb.config;

import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private JWTRequestFilter requestFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable().cors().disable();
        http.addFilterBefore(requestFilter, AuthorizationFilter.class);
        http.authorizeHttpRequests().anyRequest().permitAll();
//        requestMatchers("/api/v1/user/addUser","/api/v1/user/login").permitAll().requestMatchers("/api/v1/Country/addCountry").hasRole("ADMIN")
//                .requestMatchers("/api/v1/user/profile").hasAnyRole("ADMIN","USER")
//                .anyRequest().authenticated();
        return http.build();
    }
}

