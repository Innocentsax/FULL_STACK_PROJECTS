package com.decagon.dev.paybuddy.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI customizeOpenApi(){
            final String securitySchemaName = "bearerAuth";
            return new OpenAPI()
                    .info(new Info()
                            .title("Pay-Buddy App API")
                            .version("1.1")
                            .description("Pay-Buddy App API Documentation")
                    )
                    .addSecurityItem(new SecurityRequirement().addList(securitySchemaName))
                    .components(new Components()
                            .addSecuritySchemes(securitySchemaName, new SecurityScheme()
                                    .name(securitySchemaName)
                                    .type(SecurityScheme.Type.HTTP)
                                    .scheme("bearer")
                                    .bearerFormat("JWT")));
        }
}
