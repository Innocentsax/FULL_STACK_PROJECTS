package com.decagon.safariwebstore.utils.mailService;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Component;

public interface MailService {
    JsonNode sendMessage(String to, String subject, String messageBody) throws UnirestException;
}
