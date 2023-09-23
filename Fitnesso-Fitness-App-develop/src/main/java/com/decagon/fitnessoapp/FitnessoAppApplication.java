package com.decagon.fitnessoapp;

import com.decagon.fitnessoapp.Email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FitnessoAppApplication {


    public static void main(String[] args) {
        SpringApplication.run(FitnessoAppApplication.class, args);
    }
}
