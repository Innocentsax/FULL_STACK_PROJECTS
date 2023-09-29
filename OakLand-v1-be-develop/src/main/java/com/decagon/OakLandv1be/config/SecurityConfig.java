package com.decagon.OakLandv1be.config;

import com.decagon.OakLandv1be.config.jwt.JWTCoder;
import com.decagon.OakLandv1be.config.userDetails.AppUserDetailsService;
import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.enums.BaseCurrency;
import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.enums.Role;
import com.decagon.OakLandv1be.repositries.CustomerRepository;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import static com.decagon.OakLandv1be.enums.Role.*;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final String[] WHITE_LISTED_URLS = { "/", "/home", "index", "/css/*", "/js/*", "/api/v1/products/**", "/api/v1/pickup/**",
            "/api/v1/auth/**","/v2/api-docs/**", "/v3/api-docs/**","/configuration/**",
            "/swagger*/**","/swagger-ui/**","/webjars/**", "/swagger-ui.html", "/api/v1/customer/signup","/api/v1/customer/verifyRegistration/**",
             "/api/v1/category/**", "/api/v1/subcategory/**", "/api/v1/finalizeTrans/**", "/api/v1/state/**", "/api/v1/products/new-arrivals",
                     "/api/v1/products/best-selling", "/api/v1/customer/resendVerificationToken/**"
    };
    private final AppUserDetailsService appUserDetailsService;
    private static final String AUTHORITY_PREFIX = "ROLE_";
    private static final String CLAIM_ROLES = "roles";
    private final PasswordEncoder passwordEncoder;
    private final JWTCoder jwtCoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.cors().configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }).and().csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests((auth) -> {
                auth.antMatchers(WHITE_LISTED_URLS).permitAll()
                        .antMatchers("/api/v1/super-admin/**").hasRole(SUPERADMIN.name())
                        .antMatchers("/api/v1/admin/**", "/api/v1/customer/admin/**").hasAnyRole(ADMIN.name(), SUPERADMIN.name())
                        .antMatchers("/api/v1/category/admin/**", "/api/v1/subcategory/admin/**").hasAnyRole(ADMIN.name(), SUPERADMIN.name())
                        .antMatchers("/api/v1/customer/**", "/api/v1/auth/update-password", "api/v1/cart/**").hasAnyRole(CUSTOMER.name())
                        .anyRequest().authenticated();
                })
                .oauth2ResourceServer(oauth2ResourceServer ->
                        oauth2ResourceServer
                                .jwt(jwt ->
                                        jwt.jwtAuthenticationConverter(getJwtAuthenticationConverter()))
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .httpBasic().disable()
                .build();
    }

    private Converter<Jwt, AbstractAuthenticationToken> getJwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(getJwtGrantedAuthoritiesConverter());
        return jwtAuthenticationConverter;
    }

    private Converter<Jwt, Collection<GrantedAuthority>> getJwtGrantedAuthoritiesConverter() {
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthorityPrefix(AUTHORITY_PREFIX);
        converter.setAuthoritiesClaimName(CLAIM_ROLES);
        return converter;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(appUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    JwtAuthenticationProvider jwtTokenAuthProvider() {
        JwtAuthenticationProvider provider = new JwtAuthenticationProvider(jwtCoder.jwtDecoder());
        provider.setJwtAuthenticationConverter(getJwtAuthenticationConverter());
        return provider;
    }

//    @Bean
//    public CommandLineRunner commandLineRunner(PersonRepository personRepository, CustomerRepository customerRepository) {
//        return args -> {
//            Person person = Person.builder()
//                    .firstName("Maria")
//                    .lastName("Girl")
//                    .password(passwordEncoder.encode("password1234"))
//                    .email("maria@gmail.com")
//                    .gender(Gender.OTHER)
//                    .date_of_birth("12-09-1997")
//                    .phone("78573944844")
//                    .verificationStatus(true)
//                    .address("Foolish")
//                    .role(Role.ADMIN)
//                    .isActive(true)
//                    .build();
//
//            Customer customer = Customer.builder()
//                    .person(person)
//                    .cart(new Cart())
//                    .wallet(Wallet.builder()
//                            .accountBalance(BigDecimal.valueOf(4000D))
//                            .baseCurrency(BaseCurrency.POUNDS)
//                            .build())
//                    .build();
//
//            personRepository.save(person);
//        };
//    }
}
