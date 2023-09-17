package com.wakacast.controllers;

import com.wakacast.configurations.security_configs.JwtAuthenticationEntryPoint;
import com.wakacast.configurations.security_configs.JwtTokenUtil;
import com.wakacast.dto.ResetPasswordDto;
import com.wakacast.services.ForgetPasswordService;
import com.wakacast.services.service_impl.JwtUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ForgetPasswordController.class)
class ForgetPasswordControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ForgetPasswordService forgetPasswordService;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    @MockBean
    private JwtUserDetailsService userDetailsService;
    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Test
    void generateResetToken() throws Exception {

        MvcResult result = mvc.perform(get("/generateTokenAndMail?email=user@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        int statusCode = result.getResponse().getStatus();
        assertEquals(200, statusCode);
    }

    @Test
    void resetPassword() throws Exception {
        ResetPasswordDto resetPasswordDto = new ResetPasswordDto();
        resetPasswordDto.setNewPassword("David@2021");

        ObjectMapper objectMapper = new ObjectMapper();
        MvcResult result = mvc.perform(post("/resetPassword/token")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(resetPasswordDto)))
                .andReturn();
        int statusCode = result.getResponse().getStatus();
        assertEquals(200, statusCode);
    }
}