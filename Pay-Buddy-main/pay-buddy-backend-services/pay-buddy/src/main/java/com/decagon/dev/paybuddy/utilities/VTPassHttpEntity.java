package com.decagon.dev.paybuddy.utilities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Ikechi Ucheagwu
 * @created 08/03/2023 - 12:09
 * @project Pay-Buddy
 */

@Component
public class VTPassHttpEntity<T> {
    @Value("${vtpass.api.key}")
    private String API_KEY;
    @Value("${vtpass.secret.key}")
    private String SECRET_KEY;
    @Value("${vtpass.public.key}")
    private String PUBLIC_KEY;

    public HttpEntity<T> getEntity(T body) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("api-key", API_KEY);
        headers.add("secret-key", SECRET_KEY);
        headers.add("public-key", PUBLIC_KEY);
        headers.setContentType((MediaType.APPLICATION_JSON));
        return new HttpEntity<>(body, headers);
    }
}
