package com.wakacast.services.service_impl;

import com.wakacast.requests.EmailRequest;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
@Slf4j
public class EmailService {

    private final SendGrid sendGrid;

    @Autowired
    public EmailService(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
    }

    public Response sendEmail(EmailRequest emailrequest) throws IOException {

        Mail mail = new Mail(new Email("info@wakacast.com"), emailrequest.getSubject(), new Email(emailrequest.getTo()),new Content("text/plain", emailrequest.getBody()));
        mail.setReplyTo(new Email("@gmail.com"));
        Request request = new Request();

        Response response = null;

        try {

            request.setMethod(Method.POST);

            request.setEndpoint("mail/send");

            request.setBody(mail.build());

            response= this.sendGrid.api(request);

        } catch (IOException ex) {

            log.error(ex.getMessage());
            throw ex;

        }

        return response;


    }


}
