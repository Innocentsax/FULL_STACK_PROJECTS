package com.decagon.adire.mail;

import com.decagon.adire.entity.Designer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;


public interface EmailService {

     void sendForgotPasswordEmail(Designer user, String otp) throws Exception;

    //FOR account creation
    void sendAccountCreationNotification(String firstname, String lastname, String email);
}
