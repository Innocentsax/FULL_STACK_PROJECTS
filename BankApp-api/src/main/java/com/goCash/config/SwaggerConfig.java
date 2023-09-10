//package com.goCash.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.*;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.contexts.SecurityContext;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.util.Collections;
//import java.util.List;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//    @Bean
//    public Docket api() {
//        List<SecurityScheme> securitySchemes = new java.util.ArrayList<>();
//        securitySchemes.add(apiKey());
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build()
//                .apiInfo(metaInfo())
//                .securityContexts(Collections.singletonList(securityContext()))
//                .securitySchemes(securitySchemes);
//    }
//
//    private ApiKey apiKey() {
//        return new ApiKey("Bearer", "Authorization", "header");
//    }
//
//    private SecurityContext securityContext() {
//        return SecurityContext.builder().securityReferences(defaultAuth()).build();
//    }
//
//    private List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        List<SecurityReference> securityReferences = new java.util.ArrayList<>();
//        securityReferences.add(new SecurityReference("JWT", authorizationScopes));
//        return securityReferences;
//    }
//
//    //Meta data for swagger
//    private ApiInfo metaInfo() {
//        return new ApiInfo(
//                "goCash FinTech API",
//                "This Application manages by SQ15 java stack",
//                "1.0.0",
//                "Terms of Server",
//                new Contact("My LinkedIn Account", "https://www.linkedin.com/in/zurum-ogbonda/", "ogbondachristian@gmail.com"),
//                "Apache License Version 2.0",
//                "http://www.apache.org/licenses/LICENSE-2.0.html",
//                Collections.emptyList()
//        );
//    }
//}
