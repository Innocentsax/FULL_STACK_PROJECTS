package com.decagon.kindredhairapigrp1.utils.security;

import com.decagon.kindredhairapigrp1.services.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Method adds custom spring security authentication setting to the app
 */
@Configuration
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
    private MyUserDetailsService myUserDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtRequestFilter jwtRequestFilter;


    SecurityConfigurer(MyUserDetailsService myUserDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder, JwtRequestFilter jwtRequestFilter){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.myUserDetailsService = myUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Method handles user login authentication
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    /**
     * Method acts as a filter determining routes that can be accessed based on user authorisation
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().headers().frameOptions().disable();
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * Method acts as a filter determining routes that can be accessed without authentication
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity security) throws Exception {
            security.ignoring()
                .antMatchers( "/register", "/auth/**", "/h2/**", "/",
                        "/swagger-resources/**", "/swagger-ui.html",
                        "/v2/api-docs", "/webjars/**");
    }
}
