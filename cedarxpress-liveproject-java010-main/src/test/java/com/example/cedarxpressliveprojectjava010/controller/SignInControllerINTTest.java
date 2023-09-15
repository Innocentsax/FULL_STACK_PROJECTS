package com.example.cedarxpressliveprojectjava010.controller;

import com.example.cedarxpressliveprojectjava010.dto.LoginDTO;
import com.example.cedarxpressliveprojectjava010.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ContextConfiguration(classes = {SignInController.class})
@ExtendWith(SpringExtension.class)
class SignInControllerINTTest {
    @Autowired
    private SignInController signInController;
    @MockBean
    private LoginService loginService;
    @MockBean
    private Authentication authentication;

    private LoginDTO loginDTO;

    @BeforeEach
    public void setUp() {

        loginDTO = new LoginDTO("user@gmail.com", "1234");
    }

    @DisplayName("Integration Test for log in")
    @Test
    public void logInTest() throws Exception {
        given(loginService.login((LoginDTO) any())).willReturn(authentication);
        String content = (new ObjectMapper()).writeValueAsString(loginDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.signInController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }

    @DisplayName("Integration Test for log out")
    @Test
    public void logOutTest() throws Exception {
        Authentication auth = new TestingAuthenticationToken("user@gmail.com", "", "ROLE_ADMIN");
        String content = (new ObjectMapper()).writeValueAsString(auth);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/log-out")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.signInController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }
}