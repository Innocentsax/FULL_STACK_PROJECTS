package com.fintech.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.app.request.UserRequest;
import com.fintech.app.response.BaseResponse;
import com.fintech.app.response.UserResponse;
import com.fintech.app.service.UserService;
import com.fintech.app.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @Autowired
    private UserController userController;

    private HttpServletRequest request;

    @MockBean
    private UserService userService;

    @MockBean
    private Util utility;

    UserRequest userRequest;
    UserResponse userResponse;

    @BeforeEach
    void setUp() {
        userRequest = UserRequest.builder()
                .firstName("fintech")
                .lastName("application")
                .email("fintech@gmail.com")
                .bvn("1234567891")
                .phoneNumber("1234567890")
                .password("fintechapplication")
                .confirmPassword("fintechapplication")
                .build();

        userResponse = UserResponse.builder()
                .id(1L)
                .firstName("fintech")
                .lastName("application")
                .email("fintech@gmail.com")
                .bvn("1234567891")
                .phoneNumber("1234567890")
                .token("")
                .status(false)
                .build();

    }

    @Test
    void createUserAccount() throws Exception {
        when(userService.createUserAccount(userRequest, request))
                .thenReturn(new BaseResponse<>(
                        HttpStatus.CREATED,
                        "Registration success",
                        userResponse));

        String content = (new ObjectMapper()).writeValueAsString(userRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(status().is(200));
    }
}