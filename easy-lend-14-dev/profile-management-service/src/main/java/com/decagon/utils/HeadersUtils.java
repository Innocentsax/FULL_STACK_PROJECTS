package com.decagon.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HeadersUtils {
    @Value("${application.security.paystack.key}")
    private static String KEY;
    private HttpHeaders headers;
    HeadersUtils(HttpHeaders headers){
        this.headers=headers;
    }
    public static String secreteKey(){
        return KEY;
    }

    public static void apiDetails(HttpHeaders headers) {
        headers.setBearerAuth(PaymentUtils.getSecretKey());
        headers.set("Cache-Control", "no-cache");
        headers.setContentType(MediaType.APPLICATION_JSON);

    }
}
