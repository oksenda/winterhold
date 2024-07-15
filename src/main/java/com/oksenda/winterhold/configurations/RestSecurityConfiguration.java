package com.oksenda.winterhold.configurations;

import com.oksenda.winterhold.auth.jwt.JwtRequestFillter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
@Configuration
@EnableWebSecurity
public class RestSecurityConfiguration {
    private final JwtRequestFillter jwtRequestFillter;

    public RestSecurityConfiguration(JwtRequestFillter jwtRequestFillter) {
        this.jwtRequestFillter = jwtRequestFillter;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.securityMatcher("/api/**")
        .csrf((request) -> request.disable())
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/resources/**","/swagger-ui/**").permitAll()
                                .requestMatchers(HttpMethod.POST,"/api/v1/auth/**").permitAll()
                                .anyRequest().authenticated()
                )
                .httpBasic((entry)->entry.authenticationEntryPoint(authenticationEntryPoint()))
                .exceptionHandling((exception)->exception.accessDeniedHandler(accessDeniedHandler()))
                .sessionManagement((session)->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFillter, UsernamePasswordAuthenticationFilter.class)
                .cors((corsConfigurer) -> corsConfigurer.configurationSource(corsConfigurationSource()));
        return httpSecurity.build();
    }



    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8081"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
    private AuthenticationEntryPoint authenticationEntryPoint(){
        return ((request, response, authException) -> {
            response.setStatus(401);
            response.getWriter().write("unautorize");
        });
    }
    private AccessDeniedHandler accessDeniedHandler(){
        return ((request, response, authException) -> {
            response.setStatus(403);
            response.getWriter().write("acses dinaide");
        });
    }
}
