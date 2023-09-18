package com.example.decapay.controllers.auth;

import com.example.decapay.configurations.security.CustomUserDetailService;
import com.example.decapay.configurations.security.JwtAuthFilter;
import com.example.decapay.pojos.requestDtos.LoginRequestDto;
import com.example.decapay.services.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserAuthControllerTest.class)
class UserAuthControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private UserAuthController userAuthController;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private  UserServiceImpl userService;

    @MockBean
    private CustomUserDetailService customUserDetailService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @WithMockUser("seunskii")
    @Test
    void signIn() {
        LoginRequestDto loginRequestDto = new LoginRequestDto("oluseun@gmail.com","oluseun1");
        try{
            String requestBody = objectMapper.writeValueAsString(loginRequestDto);
            mockMvc.perform(post("/api/v1/auth/signin")
                    .contentType(MediaType.APPLICATION_JSON).with(csrf()).content(requestBody))
                    .andExpect(status().isOk());

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}