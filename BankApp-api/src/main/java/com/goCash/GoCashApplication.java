package com.goCash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class GoCashApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoCashApplication.class, args);
	}

}
