package com.zidiogroup9.expensemanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private static final String[] PUBLIC_ROUTS = {"/error","api/auth/**"};
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(PUBLIC_ROUTS).permitAll()
                                .anyRequest().authenticated())
                .csrf(csrfConfig -> csrfConfig.disable());
        return httpSecurity.build();
    }
}
