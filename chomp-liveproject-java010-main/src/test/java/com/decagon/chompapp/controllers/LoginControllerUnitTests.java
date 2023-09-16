package com.decagon.chompapp.controllers;


import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import com.decagon.chompapp.dtos.LoginDto;
import com.decagon.chompapp.services.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
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

@ContextConfiguration(classes = {LoginController.class})
@ExtendWith(SpringExtension.class)
class LoginControllerUnitTests {
    @Autowired
    private LoginController loginController;

    @MockBean
    private LoginService loginService;

    @Test
    void testLogin() throws Exception {
        when(this.loginService.login((LoginDto) any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("adekunle@gmail.com");
        loginDto.setPassword("1234");
        String content = (new ObjectMapper()).writeValueAsString(loginDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.loginController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    public void testLogout() throws Exception {
        when(loginService.logout(any())).thenReturn(null);

        ResponseEntity<?> result = loginService.logout(null);
        Assertions.assertNull(result);
    }

}

