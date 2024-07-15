package com.oksenda.winterhold.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFillter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    public JwtRequestFillter(JwtService jwtService, UserDetailsService userDetailsService, AuthenticationFailureHandler authenticationFailureHandler) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            var authorizatinoHeader= request.getHeader("Authorization");
            if (authorizatinoHeader != null && authorizatinoHeader.startsWith("Bearer ")){
                var token = authorizatinoHeader.substring(7);
                var claims =jwtService.getClaims(token);
                var username = claims.get("username",String.class);
                var userDetails = userDetailsService.loadUserByUsername(username);
                Boolean isValid = jwtService.isValid(token, userDetailsService.loadUserByUsername(username));
                if (isValid){
                    var authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }catch (Exception exception){
            authenticationFailureHandler.onAuthenticationFailure(request,response, new BadCredentialsException("token Is invalid"));
            return;
        }
        filterChain.doFilter(request,response);
    }
}
