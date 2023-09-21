package com.decagon.eventhubbe.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {
    public static String getUserEmailFromContext(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
