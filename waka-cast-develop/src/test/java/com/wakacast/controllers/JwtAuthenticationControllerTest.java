package com.wakacast.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wakacast.configurations.security_configs.JwtAuthenticationEntryPoint;
import com.wakacast.configurations.security_configs.JwtTokenUtil;
import com.wakacast.repositories.UserRepository;
import com.wakacast.requests.JwtRequest;
import com.wakacast.services.AdminService;
import com.wakacast.services.service_impl.JwtUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(JwtAuthenticationController.class)
class JwtAuthenticationControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    @MockBean
    private JwtUserDetailsService userDetailsService;
    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @MockBean
    private UserDetails userDetails;
    @MockBean
    private AdminService adminService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ModelMapper mapper;

    @Test
    void createAuthenticationToken() throws Exception {
        JwtRequest jwtRequest = new JwtRequest("og@gmail.com", "12345678");
        ObjectMapper objectMapper = new ObjectMapper();
        mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(jwtRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void confirmLoginToken() throws Exception {
        mvc.perform(post("/admin/confirm-token/")
                        .param("loginToken", "123456"))
                .andExpect(status().isOk());
    }
}