package com.decagon.borrowerservice;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BorrowerServiceApplication {

	@Bean
	public ModelMapper modelMapper() {return new ModelMapper();}

	public static void main(String[] args) {
		SpringApplication.run(BorrowerServiceApplication.class, args);
	}

}