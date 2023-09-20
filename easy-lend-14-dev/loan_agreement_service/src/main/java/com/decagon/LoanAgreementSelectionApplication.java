package com.decagon;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.decagon.loanagreementservice.*")
@EnableFeignClients
public class LoanAgreementSelectionApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoanAgreementSelectionApplication.class, args);
    }
}