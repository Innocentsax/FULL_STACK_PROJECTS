//package com.decagon.loanagreementservice.security_config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//
//
//public class WebConfig implements WebMvcConfigurer {
//    private final JwtFilter jwtFilter;
//
//
//    public WebConfig(JwtFilter jwtFilter) {
//        this.jwtFilter = jwtFilter;
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jwtFilter).addPathPatterns("/api/v1/borrower/**", "/api/v1/lender/**");
//    }
//
//}
