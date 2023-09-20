package com.decagon.borrowerservice.utils;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecuritySchemes({@SecurityScheme(name = "bearerToken", type = SecuritySchemeType.HTTP,
        scheme = "bearer", bearerFormat = "JWT")})
public class SwaggerConfig {
    @Value("${swagger.version}")
    private String version;
    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info()
                        .title("Borrower Service API")
                        .description("API documentation for Borrower Service")
                        .version(version));
    }

    @Bean
    public GroupedOpenApi borrowersEndpoint() {
        return GroupedOpenApi
                .builder()
                .group("apply")
                .pathsToMatch("/api/loanApplication/apply**").build();
    }
}
 