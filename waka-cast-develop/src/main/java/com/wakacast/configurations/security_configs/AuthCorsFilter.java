package com.wakacast.configurations.security_configs;

import org.springframework.util.Assert;
import org.springframework.web.cors.*;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthCorsFilter extends OncePerRequestFilter {
    private final CorsConfigurationSource configSource;

    private final CorsProcessor processor = new DefaultCorsProcessor();

    public AuthCorsFilter(CorsConfigurationSource configSource) {
        Assert.notNull(configSource, "CorsConfigurationSource must not be null");
        this.configSource = configSource;
    }

    public static UrlBasedCorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", configuration);

        return corsConfigurationSource;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        CorsConfiguration corsConfiguration = this.configSource.getCorsConfiguration(request);
        boolean isValid = this.processor.processRequest(corsConfiguration, request, response);
        if (!isValid) {
            return;
        }
        filterChain.doFilter(request, response);
    }
}
