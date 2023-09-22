package com.decagon.fintechpaymentapisqd11a.configuration;

import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfig {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private ApiInfo apiInfo() {
        return new ApiInfo("Fintech Payment Application",
                "A full fledge website",
                "1.0",
                "Free to use and authenticate for protected endpoints",
                new Contact("Java Live Project", "www.https://decagonhq.com", "javag1liveproject@gmail.com"),
                "License of API",
                "www.https://decagonhq.com",
                Collections.emptyList());
    }
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.decagon.fintechpaymentapisqd11a"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiKey apiKey() {
        return new ApiKey("Bearer", AUTHORIZATION_HEADER, "header");
    }
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }
    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Bearer", authorizationScopes));
    }
}
