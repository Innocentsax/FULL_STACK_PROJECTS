package com.decagon.fintechpaymentapisqd11a.services.serviceImpl;

import com.decagon.fintechpaymentapisqd11a.util.JwtUtils;
import com.decagon.fintechpaymentapisqd11a.dto.LoginRequestPayload;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {LoginServiceImpl.class})
@ExtendWith(SpringExtension.class)
class LoginServiceImplTest {
    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private LoginServiceImpl loginServiceImpl;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    void testAuthenticate() throws Exception {
        when(jwtUtils.generateToken(any())).thenReturn("ABC123");
        when(authenticationManager.authenticate(any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(userDetailsService.loadUserByUsername(any()))
                .thenReturn(new User("janedoe", "iloveyou", new ArrayList<>()));
        assertEquals("ABC123", loginServiceImpl.authenticate(new LoginRequestPayload("jane.doe@example.org", "iloveyou")));
        verify(jwtUtils).generateToken(any());
        verify(authenticationManager).authenticate(any());
        verify(userDetailsService).loadUserByUsername(any());
    }


    @Test
    void testAuthenticateThrowsException() throws Exception {
        when(jwtUtils.generateToken(any())).thenReturn("ABC123");
        when(authenticationManager.authenticate(any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(userDetailsService.loadUserByUsername(any())).thenThrow(new BadCredentialsException("Msg"));
        assertThrows(BadCredentialsException.class,
                () -> loginServiceImpl.authenticate(new LoginRequestPayload("jane.doe@example.org", "iloveyou")));
        verify(authenticationManager).authenticate(any());
        verify(userDetailsService).loadUserByUsername(any());
    }

}

