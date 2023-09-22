package com.decagon.fintechpaymentapisqd11a.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.decagon.fintechpaymentapisqd11a.dto.RegistrationRequestDto;
import com.decagon.fintechpaymentapisqd11a.services.serviceImpl.RegistrationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @MockBean
    private RegistrationServiceImpl registrationServiceImpl;

    @Autowired
    private UserController userController;


    @Test
    void testConfirmToken() throws Exception {
        when(registrationServiceImpl.confirmToken((String) any())).thenReturn("ABC123");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/user/confirm")
                .param("token", "foo");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("ABC123"));
    }


    @Test
    void testCreateUserAccount() throws Exception {
        when(registrationServiceImpl.createUser((RegistrationRequestDto) any())).thenReturn("Create User");

        RegistrationRequestDto registrationRequestDto = new RegistrationRequestDto();
        registrationRequestDto.setBvn("Bvn");
        registrationRequestDto.setConfirmPassword("iloveyou");
        registrationRequestDto.setEmail("jane.doe@example.org");
        registrationRequestDto.setFirstName("Jane");
        registrationRequestDto.setLastName("Doe");
        registrationRequestDto.setPassword("iloveyou");
        registrationRequestDto.setPhoneNumber("4105551212");
        registrationRequestDto.setTransactionPin("Transaction Pin");
        String content = (new ObjectMapper()).writeValueAsString(registrationRequestDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/user/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Create User"));
    }
}

