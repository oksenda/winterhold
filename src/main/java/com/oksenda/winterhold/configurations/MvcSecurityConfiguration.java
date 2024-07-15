package com.oksenda.winterhold.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MvcSecurityConfiguration {
    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request ->
                request.requestMatchers("/resources/**","/register/**","/login","/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/webjars/**", "/swagger-resources/**").permitAll()
                        .anyRequest().authenticated()
                ).formLogin((formLogin)-> formLogin
                        .loginPage("/login")
                        .failureUrl("/login?error=true")
                        .loginProcessingUrl("/authenticating"))
                .logout(logout->logout
                        .logoutUrl("/logout")
                        );
        return httpSecurity.build();
    }

}
