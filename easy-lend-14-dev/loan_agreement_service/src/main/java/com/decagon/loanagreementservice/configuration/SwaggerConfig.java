package com.decagon.loanagreementservice.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI api(){
        return new OpenAPI()
                .info(new Info()
                        .title("Loan Agreement and Selection API")
                        .description("Provides users with endpoints to select loans they desire to either fund or receive.")
                        .version("1.0.0"));
    }


    @Bean
    public GroupedOpenApi borrowerApi() {
        return GroupedOpenApi.builder()
                .group("borrower-api")
                .pathsToMatch("/api/v1/borrower/**")
                .build();
    }

    @Bean
    public GroupedOpenApi lenderApi() {
        return GroupedOpenApi.builder()
                .group("lender-api")
                .pathsToMatch("/api/v1/lender/**")
                .build();
    }
}



