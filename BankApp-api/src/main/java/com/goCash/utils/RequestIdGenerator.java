package com.goCash.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class RequestIdGenerator {

    public static String RequestID(){
        String input = LocalDateTime.now().toString();

        String formattedDateTime;

        String pattern = "(\\d{4})-(\\d{2})-(\\d{2})T(\\d{2}):(\\d{2}).*";

        Pattern regexPattern = Pattern.compile(pattern);

        Matcher matcher = regexPattern.matcher(input);

        if (matcher.matches()) {
            String year = matcher.group(1);
            String month = matcher.group(2);
            String day = matcher.group(3);
            String hour = matcher.group(4);
            String minute = matcher.group(5);

            formattedDateTime = year + month + day + hour + minute;

            String randomString = ReferenceGenerator.generateRandomString(6);

            return formattedDateTime + randomString;
        }
        log.info("Input does not match the expected format.");
        return "";
    }
}
