package com.example.hive.documentation;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
@OpenAPIDefinition
public class SwaggerConfig {

    // Get the Dependencies, setup docs

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("HIVE Application API")
                        .description("Hive is a Platform that connects Taskers and Doers. " +
                                "Taskers are users who have a task or a service they need to get done eg; " +
                                "grocery shopping, delivery, cleaning etc. While doers are users who sign " +
                                "up to take up these tasks and get them done in order to make extra income. ")
                        .version("v0.0.1")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org"))
                        .contact(new Contact()
                                .email("decagon@gmail.com")
                                .name("SQ'13 LIVE PROJECT")
                                .url("https://github.com/decadevs/HIVE-BACKEND-SQ013")
                        )
                )
                .components(new Components()
                        .addSecuritySchemes("bearer",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT"))
                );



    }



}