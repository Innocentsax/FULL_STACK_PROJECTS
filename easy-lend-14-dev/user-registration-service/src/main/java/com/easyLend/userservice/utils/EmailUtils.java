package com.easyLend.userservice.utils;

import jakarta.servlet.http.HttpServletRequest;

public class EmailUtils {
    public static String applicationUrl(HttpServletRequest request){
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }
    public static String ReactUrl(HttpServletRequest request){
        return "http://"+request.getServerName()+":"+5173+request.getContextPath();
    }
}
