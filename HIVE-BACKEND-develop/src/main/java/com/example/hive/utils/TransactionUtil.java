package com.example.hive.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.UUID;

public class TransactionUtil {
    public static HttpHeaders getAuthHeader(String auth){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(auth);
        return headers;
    }

    public static String generateUniqueRef(){
        return UUID.randomUUID().toString();
    }
}
