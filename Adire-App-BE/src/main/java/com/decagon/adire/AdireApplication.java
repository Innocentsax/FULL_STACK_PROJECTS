package com.decagon.adire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class AdireApplication {


    public static void main(String[] args) {
        SpringApplication.run(AdireApplication.class, args);
        //refreshing server

    }


}
