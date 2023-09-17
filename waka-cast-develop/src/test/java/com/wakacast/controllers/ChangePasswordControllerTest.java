package com.wakacast.controllers;

import com.wakacast.configurations.security_configs.JwtAuthenticationEntryPoint;
import com.wakacast.configurations.security_configs.JwtTokenUtil;
import com.wakacast.dto.ChangePasswordDto;
import com.wakacast.services.ChangePasswordService;
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

import javax.servlet.http.HttpServletRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ChangePasswordController.class)
class ChangePasswordControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private JwtUserDetailsService userDetailsService;
    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @MockBean
    private ChangePasswordService changePasswordService;
    @Autowired
    private HttpServletRequest request;


    @Test
    void updatePassword () throws Exception {
        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        changePasswordDto.setOldPassword("123456789");
        changePasswordDto.setNewPassword("87654321");

        ObjectMapper objectMapper = new ObjectMapper();
        mvc.perform(put("/changePassword")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(changePasswordDto)))
                .andExpect(status().isOk());

    }
}
