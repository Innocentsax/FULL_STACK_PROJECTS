package com.example.cedarxpressliveprojectjava010.config.security;

import com.example.cedarxpressliveprojectjava010.config.jwt.JWTAuthenticationFilter;
import com.example.cedarxpressliveprojectjava010.config.jwt.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {



    @Autowired
    private UserDetailsService userDetailsService;


    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")

                .antMatchers("/user").hasAnyRole("USER","ADMIN")

                .antMatchers("/", "cerderXpress/user/register", "/forgot-password").permitAll()

                .antMatchers("/user").hasAnyRole("ROLE_CUSTOMER","ADMIN")
                .antMatchers("/", "cerderXpress/user/register",

                        "/reset-password/**", "/login", "home","/swagger-ui/**",
                        "/swagger-resources/**", "/swagger-ui/index.html#")
                .permitAll()
                .antMatchers("/products/**").permitAll()
                .antMatchers("/forgot-password")
                .permitAll();

    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
