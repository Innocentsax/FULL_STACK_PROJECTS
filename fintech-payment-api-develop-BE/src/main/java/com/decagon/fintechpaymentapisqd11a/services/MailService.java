package com.decagon.fintechpaymentapisqd11a.services;

import com.decagon.fintechpaymentapisqd11a.dto.MailServiceDto;
import org.springframework.mail.MailException;

public interface MailService {

    public void sendNotification(MailServiceDto mailServiceDto) throws MailException;
}

