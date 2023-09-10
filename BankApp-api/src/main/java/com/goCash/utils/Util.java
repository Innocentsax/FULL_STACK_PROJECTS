package com.goCash.utils;

import com.goCash.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class Util {

    public String  getLoginUser (){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String loggedInUserUserName = userDetails.getUsername();
        return loggedInUserUserName;
    }
}
