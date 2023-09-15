package com.example.cedarxpressliveprojectjava010.controller;

import com.example.cedarxpressliveprojectjava010.dto.request.ForgotPasswordRequest;
import com.example.cedarxpressliveprojectjava010.dto.request.ResetPasswordRequest;
import com.example.cedarxpressliveprojectjava010.dto.response.MessageResponse;
import com.example.cedarxpressliveprojectjava010.service.UserManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UserManagementController.class})
@ExtendWith(SpringExtension.class)
class UserManagementControllerTest {

    @Autowired
    private UserManagementController userManagementController;

    @MockBean
    private UserManagementService userManagementService;

    @MockBean
    private ForgotPasswordRequest request;

    @MockBean
    private ResetPasswordRequest resetPasswordRequest;

    @MockBean
    private MessageResponse response;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void shouldGetForgotPasswordLink() throws Exception {

        //given
        request = ForgotPasswordRequest.builder()
                .email("johndoe@gmail.com")
                .build();

        //when
        when(userManagementService.forgotPassword(request)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        String content = (new ObjectMapper()).writeValueAsString(request);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/forgot-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userManagementController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));

    }

    @Test
    void shouldResetPassword() throws Exception {
        String jwtToken = "bejbvjubovjebovjebof";
        resetPasswordRequest = ResetPasswordRequest.builder()
                .email("johndoe@gmail.com")
                .newPassword("pass")
                .confirmPassword("pass")
                .build();

        when(userManagementService.resetPassword(jwtToken, resetPasswordRequest)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        String content = (new ObjectMapper()).writeValueAsString(resetPasswordRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/reset-password/bejbvjubovjebovjebof")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userManagementController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk());


    }
}