package com.wakacast.global_constants;

public class Constants {
    public static final String BASE_URL = "https://wakacast-v2.herokuapp.com/";
    public static final long JWT_TOKEN_VALIDITY= 50L*60*60;
    public static final long JWT_TOKEN_EXPIRATION_DATE = JWT_TOKEN_VALIDITY*120000;
    public static final String USER_NOT_FOUND = "User with this email was not found";


    private Constants() {
    }
}
