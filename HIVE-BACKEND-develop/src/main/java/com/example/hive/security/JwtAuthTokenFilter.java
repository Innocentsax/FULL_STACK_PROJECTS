package com.example.hive.security;


import com.example.hive.constant.SecurityConstants;
import com.example.hive.exceptions.CustomException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Component
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, CustomException {
        if (request.getServletPath().equals("/auth/signup") || request.getServletPath().equals("/auth/login") ) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
                try {
                    log.info("I want to verify {}", authorizationHeader);
                    String token = authorizationHeader.substring(SecurityConstants.TOKEN_PREFIX.length());
                    UsernamePasswordAuthenticationToken authenticationToken = JwtService.verifyToken(token);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    log.error("Error occurred {}", exception.getMessage());
                    response.setHeader("error", exception.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    throw new CustomException(exception.getMessage());
                }

            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
