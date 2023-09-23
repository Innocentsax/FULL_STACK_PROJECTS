package com.example.hive.constant;

public class SecurityConstants {

    public static String SECRET = "EdHvMcQfTjWmZq4t7wszqCyFiJaNdRgUkXp2s5u8x/AoDfG+KbPeShVmYq3t6w9y";
    public static final long EXPIRATION_TIME = 3_200_000; // 60 mins
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/auth/register";
    public static final String PASSWORD_NOT_MATCH_MSG = "Password And Confirm-Password Does Not Match";
}
