package com.decadev.money.way.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;


public class TransactionReferenceGenerator {
    public static String generateReference(){
        String randomString= UUID.randomUUID().toString().substring(0, 8);
        return"MW-"+randomString;
    }
}
