package com.decagon.adire.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
//@EnableSwagger2
public class SwaggerConfiguration implements WebMvcConfigurer {

    // WEBSITE IS - http://localhost:8080/swagger-ui/

    @Bean
    public Docket swaggerConfig() {
        //Return a prepared Docket Instance
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiDetails())
                .securityContexts(List.of(securityContext()))
                .securitySchemes(List.of(apiKey()))
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.decagon.adire"))
                .paths(PathSelectors.any())
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("swagger-ui/")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }


    private SecurityReference bearerAuthReference() {
        return new SecurityReference("Bearer", new AuthorizationScope[0]);
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Bearer", authorizationScopes));
    }


    private ApiInfo apiDetails() {
        return new ApiInfo(
                "Adire-NG API",
                "API for Adire",
                "1.0",
                "Free to use",
                new Contact("Adire", "http://adire.com", "info@adire.com"),
                "API License",
                "https://adire.com/terms.php",
                Collections.emptyList()
        );
    }


    private ApiKey apiKey() {
        return new ApiKey("Bearer", "Authorization", "header");
    }


}
