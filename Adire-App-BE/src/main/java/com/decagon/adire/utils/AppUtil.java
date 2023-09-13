package com.decagon.adire.utils;

import com.decagon.adire.dto.response.AppResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

@Slf4j
public class AppUtil {

    @Bean
    public static Optional<String> extractEmailFromPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken))
            return Optional.of(authentication.getName());
        return Optional.empty();
    }


    public static String getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "system";
        }
        return authentication.getName();
    }


    public static String generateResetPassword(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + (1000*60*30)))
                .signWith(SignatureAlgorithm.HS384, "something")
                .compact();
    }

    public static <T> ResponseEntity<AppResponse> errorResponse(HttpStatus status, String errMsg){
        AppResponse <T> ar = new AppResponse<>(status);
        ar.setMessage(errMsg);
        ar.setStatus(true);
        ar.getTime();
        return new ResponseEntity<>(ar,status);
    }




}
