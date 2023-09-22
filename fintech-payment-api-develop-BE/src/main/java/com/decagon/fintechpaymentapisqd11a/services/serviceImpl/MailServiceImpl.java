package com.decagon.fintechpaymentapisqd11a.services.serviceImpl;

import com.decagon.fintechpaymentapisqd11a.dto.MailServiceDto;
import com.decagon.fintechpaymentapisqd11a.services.MailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {


    private final JavaMailSender javaMailSender;

    @Override
    public void sendNotification(MailServiceDto mailServiceDto) throws MailException{

        //send email

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(mailServiceDto.getEmailAddress());
        mail.setSubject(mailServiceDto.getSubject());
        mail.setText("Hi " + mailServiceDto.getName() + "!\n" + mailServiceDto.getMessage());
        javaMailSender.send(mail);
    }



}

