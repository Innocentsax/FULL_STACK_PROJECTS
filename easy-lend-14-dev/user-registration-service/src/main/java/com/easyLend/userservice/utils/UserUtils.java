package com.easyLend.userservice.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {

    public static String getLogInEmail(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
