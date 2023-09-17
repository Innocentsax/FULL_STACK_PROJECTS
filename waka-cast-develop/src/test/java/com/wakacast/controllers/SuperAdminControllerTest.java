package com.wakacast.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wakacast.configurations.security_configs.JwtAuthenticationEntryPoint;
import com.wakacast.configurations.security_configs.JwtTokenUtil;
import com.wakacast.dto.AdminDto;
import com.wakacast.services.AdminService;
import com.wakacast.services.service_impl.JwtUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(SuperAdminController.class)
class SuperAdminControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    @MockBean
    private JwtUserDetailsService userDetailsService;
    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @MockBean
    private AdminService adminService;
    @MockBean
    private UserController userController;

    @Test
    void givenNewAdmin_shouldCreateANewAdmin() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        AdminDto adminDto = new AdminDto();
        mockMvc.perform(post("/api/super-admin/create-admin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(adminDto)))
                .andExpect(status().isOk());
    }

}