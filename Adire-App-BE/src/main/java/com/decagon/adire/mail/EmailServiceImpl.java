package com.decagon.adire.mail;

import com.decagon.adire.entity.Designer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;

import java.io.StringWriter;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService{

    private final VelocityEngine velocityEngine;

    private final EmailClient emailClient;

    @Override
    public void sendForgotPasswordEmail(Designer designer, String otp) throws Exception {
        Template template = velocityEngine.getTemplate("/templates/forgot_password.vm");

        VelocityContext context = new VelocityContext();
        context.put("name", designer.getFirstName());
        context.put("otp", otp);
        StringWriter stringWriter = new StringWriter();
        template.merge(context, stringWriter);

        emailClient.sendMail(designer.getEmail(), "Forgot Password", stringWriter.toString());
    }

    @Override
    public void sendAccountCreationNotification(String firstname, String lastname, String email) {
        try {
            Template template = velocityEngine.getTemplate("/templates/accountcreation.vm");
            VelocityContext context = new VelocityContext();
            context.put("name", firstname + " " + lastname);
            context.put("email", email);
            StringWriter stringWriter = new StringWriter();
            template.merge(context, stringWriter);
            emailClient.sendMail(email,"Account Creation" , stringWriter.toString());
        } catch (Exception e) {
            log.error("error sending mail - account creation notification");
        }
    }

}
