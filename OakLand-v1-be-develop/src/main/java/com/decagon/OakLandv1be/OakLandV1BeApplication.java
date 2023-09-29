package com.decagon.OakLandv1be;

import com.decagon.OakLandv1be.config.jwt.RSAKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableConfigurationProperties(RSAKeyProperties.class)
@EnableSwagger2
@CrossOrigin(origins = "http://localhost:3000")
public class OakLandV1BeApplication {

	public static void main(String[] args) {
		SpringApplication.run(OakLandV1BeApplication.class, args);
	}
}
