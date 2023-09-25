package com.decadev.money.way.flutterAPI;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;


@Component
@Data
public class RestTemplateUtils {
    private HttpHeaders headers;

    @Value("${flutter-secret-key}")
    private String secretKey;

    public HttpHeaders getHeaders(){
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization","Bearer " +secretKey);

        return headers;
    }
}
