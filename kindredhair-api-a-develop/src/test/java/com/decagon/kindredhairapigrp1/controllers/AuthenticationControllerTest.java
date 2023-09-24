package com.decagon.kindredhairapigrp1.controllers;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.decagon.kindredhairapigrp1.DTO.AuthenticationRequest;

import com.decagon.kindredhairapigrp1.models.User;
import com.decagon.kindredhairapigrp1.repository.UserRepository;
import com.decagon.kindredhairapigrp1.services.AuthenticationService;
import com.decagon.kindredhairapigrp1.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private User user;



    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("hello@hello.com");
        user.setRole("USER");
        user.setStatus("");
        user.setPhoneNumber("0909999999");
        user.setEnabled(true);
        user.setPassword(bCryptPasswordEncoder.encode("foo"));
    }

    @Test
    void checkControllers(){
        assertNotNull(authenticationService);
    }

    @Test
    @DisplayName("Should login user ")
    void createAuthenticationToken() throws Exception {

        AuthenticationRequest authenticationRequest = new AuthenticationRequest("hello@hello.com", "foo");

        when(userRepository.findByEmail("hello@hello.com")).thenReturn(Optional.of(user));

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.convertObjectToJsonBytes(authenticationRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(1));
    }

    @Test
    @DisplayName("Should not login user with wrong credentials")
    void createAuthenticationTokenWrongLoginCredentials() throws Exception {

        AuthenticationRequest authenticationRequest = new AuthenticationRequest("hello@hello.com", "foody");

        when(userRepository.findByEmail("hello@hello.com")).thenReturn(Optional.of(user));

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.convertObjectToJsonBytes(authenticationRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Incorrect login credentials"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Bad credentials"));
    }

}
