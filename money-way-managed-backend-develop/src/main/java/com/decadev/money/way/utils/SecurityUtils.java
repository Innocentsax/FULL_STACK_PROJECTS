package com.decadev.money.way.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;



public class SecurityUtils {
    public UserDetails getCurrentUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            return null;
        }
        Object principal = authentication.getPrincipal();
        if(principal != null){
            return (UserDetails) principal;
        }
        return null;
    }
}
