package com.example.hive.security.config;

import com.example.hive.security.JwtAuthTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class HiveSecurityConfig {

    private final JwtAuthTokenFilter authenticationFilter;
    private final AuthenticationProvider authenticationProvider;


    //Authorization
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


//when X-frame option is disabled it increases the risk of clickjacking
        return http.headers().frameOptions().disable().and()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/auth/**",
                        "/login",
                        "/h2-console/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/actuator/**",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/webjars/**"
                ).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
//    private final LogoutHandler logoutHandler;
//    private final LogoutSuccessHandler logoutSuccessHandler;
//    private final JwtAuthTokenFilter authenticationFilter;
//    private final PasswordEncoder passwordEncoder;
//    private final AuthenticationProvider authenticationProvider;
//
//    //Authorization
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {



        //when X-frame option is disabled it increases the risk of clickjacking
//        return http.headers().frameOptions().disable().and()
//                .csrf().disable()
//                .authorizeHttpRequests()
//                .requestMatchers(
//                        "/auth/**",
//                        "/login",
//                        "/h2-console/**",
//                        "/v3/api-docs/**",
//                        "/swagger-ui/**",
//                        "/actuator/**",
//                        "/swagger-resources/**",
//                        "/api/v1/user/**",
//                        "/transaction/**",
//                        "/tasks/search?text=book",
//                        "/tasks/search",
//                        "/tasks/**",//debugging
//                        "/swagger-ui.html",
//                        "/webjars/**",
//                        "/api/notifications/**"
//                ).permitAll()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .logout()
//                .logoutUrl("/logout")
//                .clearAuthentication(true)
//                .addLogoutHandler(logoutHandler)
//                .logoutSuccessHandler(((request, response, authentication) ->
//                        SecurityContextHolder.clearContext()))
//                .invalidateHttpSession(true)
//                .deleteCookies("JESSIONID")
//                .logoutSuccessUrl("/login")
//                .and()
//                .build();
//    }

}
