//package com.decagon.loanagreementservice.security_config;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.NonNull;
//import lombok.RequiredArgsConstructor;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import java.io.IOException;
//import java.util.UUID;
//
//@Component
//@RequiredArgsConstructor
//
//public class JwtFilter implements HandlerInterceptor {
//    private final JwtUtils jwtUtils;
//
//    @Override
//    public boolean preHandle(
//            @NonNull HttpServletRequest request,
//            @NonNull HttpServletResponse response,
//            Object handler) throws ServletException, IOException {
//
//        final String authHeader = request.getHeader("Authorization");
//
//        final String jwtToken;
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            System.out.println("anything...");
//            return true;
//        }
//        System.out.println("another thing ...");
//        jwtToken = authHeader.substring(7);
//        if (!jwtUtils.isTokenExpired(jwtToken)) {
//            final String userId = jwtUtils.getUserIdFromToken(jwtToken);
//            System.out.println("userId" + userId + ">>>>>>>>>>>>>>>>>");
//            request.setAttribute("userId", userId);
//
//        }
//        return true;
//    }
//}
//
//
//
//
