package com.decagon.fintechpaymentapisqd11a.configuration;

import com.decagon.fintechpaymentapisqd11a.util.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CustomAuthorizationFilter customAuthorizationFilter;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.authorizeHttpRequests().antMatchers( "/api/v1/user/**" ).permitAll();
        http.authorizeHttpRequests().antMatchers("/api/v1/login/**","/api/v1/registration").permitAll();
        http.authorizeHttpRequests().antMatchers(HttpMethod.GET, "/api/user/users/**").hasAnyAuthority("USER", "/api/v1/transactionHistory");
        http.authorizeHttpRequests().antMatchers("/v2/api-docs",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui/login/",
                "/swagger-ui/api/login/",
                "/swagger-ui/#/**",
                "/api/auth/**",
                "/auth/**",
                "/**").permitAll();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeHttpRequests().anyRequest().authenticated();
        http.addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}
