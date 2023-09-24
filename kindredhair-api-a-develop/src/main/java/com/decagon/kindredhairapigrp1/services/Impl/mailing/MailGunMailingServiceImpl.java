package com.decagon.kindredhairapigrp1.services.Impl.mailing;

import com.decagon.kindredhairapigrp1.services.MailingService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service("mailGun")
public class MailGunMailingServiceImpl implements MailingService {
    @Value("#{environment['mailgun.api_key']}")
    private String API_KEY;

    @Value("#{environment['mailgun.domain_name']}")
    private String DOMAIN_NAME;

    /**
     * This method handles password reset mailing, It sends password reset link to
     * the provided email address.
     * @param email
     * @param link
     * @return
     */
    @Override
    public boolean sendPasswordResetLink(String email, String link)  {

        try{
            HttpResponse<String> request = Unirest.post("https://api.mailgun.net/v3/" + DOMAIN_NAME + "/messages")
                    .basicAuth("api", API_KEY)
                    .field("from", "Kindred Hair <obrientester@gmail.com>")
                    .field("to", email)
                    .field("template", "password_reset")
                    .field("subject", "Password Reset Link")
                    .field("h:X-Mailgun-Variables", String.format("{\"reset_link\": \"%s\"}", link))
                    .asString();
            return request.getStatus() == 200;
        } catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean sendViewRecommendationLink(String email, String link) {
        try{
            HttpResponse<String> request = Unirest.post("https://api.mailgun.net/v3/" + DOMAIN_NAME + "/messages")
                    .basicAuth("api", API_KEY)
                    .field("from", "Kindred Hair <obrientester@gmail.com>")
                    .field("to", email)
                    .field("template", "view_recommendation")
                    .field("subject", "View Recommendation Link")
                    .field("h:X-Mailgun-Variables", String.format("{\"recommendation_link\": \"%s\"}", link))
                    .asString();
            return request.getStatus() == 200;
        } catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
    }
}
