package com.example.hive.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

public class StringUtil {

    private StringUtil(){}

    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus status){
        return new  ResponseEntity<>("{\" message \" : \" " + responseMessage + " \"}", status);
    }
    public static boolean doesBothStringMatch(String firstText, String secondText){
        if (Objects.nonNull(firstText) && Objects.nonNull(secondText)) {
            return Objects.equals(firstText, secondText);
        }
        return false;
    }
}
