package com.decagon.eventhubbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class EventHubBeApplication{

    public static void main(String[] args) {
        SpringApplication.run(EventHubBeApplication.class, args);
    }

}
