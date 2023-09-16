package com.decagon.chompapp.services.Impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.decagon.chompapp.dtos.JwtAuthResponse;
import com.decagon.chompapp.dtos.LoginDto;
import com.decagon.chompapp.models.BlackListedToken;
import com.decagon.chompapp.repositories.BlackListedTokenRepository;
import com.decagon.chompapp.security.JwtTokenProvider;
import com.decagon.chompapp.services.BlackListService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {LoginServiceImpl.class})
@ExtendWith(SpringExtension.class)
class LoginServiceImplTest {
    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private BlackListService blackListService;

    @MockBean
    private BlackListedTokenRepository blackListedTokenRepository;

    @MockBean
    private HttpServletRequest httpServletRequest;

    @MockBean
    private BlackListedToken blackListedToken;

    @MockBean
    private HttpServletResponse httpServletResponse;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private LoginServiceImpl loginServiceImpl;


    @Test
    void testLogin() throws Exception {
        when(this.jwtTokenProvider.generateToken((Authentication) any())).thenReturn("ABC123");
        doNothing().when(this.httpServletResponse).setHeader((String) any(), (String) any());
        when(this.authenticationManager.authenticate((Authentication) any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));

        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("adekunle@gmail.com");
        loginDto.setPassword("1234");
        ResponseEntity<JwtAuthResponse> actualLoginResult = this.loginServiceImpl.login(loginDto);
        assertTrue(actualLoginResult.hasBody());
        assertTrue(actualLoginResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualLoginResult.getStatusCode());
        assertEquals("ABC123", actualLoginResult.getBody().getAccessToken());
        verify(this.jwtTokenProvider).generateToken((Authentication) any());
        verify(this.httpServletResponse).setHeader((String) any(), (String) any());
        verify(this.authenticationManager).authenticate((Authentication) any());
    }


    @Test
    void testLogout() {
        when(this.httpServletRequest.getHeader((String) any())).thenReturn("https://decagon.com");

        BlackListedToken blackListedToken = new BlackListedToken();
        blackListedToken.setId(123L);
        blackListedToken.setToken("ABC123");
        when(this.blackListService.blackListToken((String) any())).thenReturn(blackListedToken);
        ResponseEntity<?> actualLogoutResult = this.loginServiceImpl.logout("ABC123");
        assertEquals("Logout Successful", actualLogoutResult.getBody());
        assertEquals(HttpStatus.OK, actualLogoutResult.getStatusCode());
        assertTrue(actualLogoutResult.getHeaders().isEmpty());
        verify(this.httpServletRequest).getHeader((String) any());
        verify(this.blackListService).blackListToken((String) any());
    }


}

