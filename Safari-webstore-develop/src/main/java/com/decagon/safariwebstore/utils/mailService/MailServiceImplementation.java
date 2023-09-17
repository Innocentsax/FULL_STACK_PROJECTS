package com.decagon.safariwebstore.utils.mailService;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Component;

@Component
public class MailServiceImplementation implements MailService {
    private static final String DOMAIN_NAME = "divineworld.club"; // domain for testing

    public  JsonNode sendMessage(String to, String subject, String messageBody) throws UnirestException {
        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + DOMAIN_NAME + "/messages")
                .basicAuth("api", API_KEY.API)
                .field("from", "safari@safari-webstore.com")
                .field("to", to)
                .field("subject", subject)
                .field("text", messageBody)
                .asJson();
        return request.getBody();

    }
}
