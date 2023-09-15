package com.example.cedarxpressliveprojectjava010.config.emailConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;
@Component
public class EmailConfiguration {

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(465);
        mailSender.setUsername("decsquad10@gmail.com");
        mailSender.setPassword("DecSquad10*#00");

        Properties properties = mailSender.getJavaMailProperties();

        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.debug","true");

        return mailSender;
    }

}
