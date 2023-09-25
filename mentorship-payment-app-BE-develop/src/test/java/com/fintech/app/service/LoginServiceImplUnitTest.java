package com.fintech.app.service;

import com.fintech.app.model.BlacklistedToken;
import com.fintech.app.model.User;
import com.fintech.app.repository.BlacklistedTokenRepository;
import com.fintech.app.repository.UserRepository;
import com.fintech.app.request.LoginRequest;
import com.fintech.app.request.PasswordRequest;
import com.fintech.app.response.BaseResponse;
import com.fintech.app.response.JwtAuthResponse;
import com.fintech.app.security.CustomUserDetailsService;
import com.fintech.app.security.JwtTokenProvider;
import com.fintech.app.service.impl.LoginServiceImpl;
import com.fintech.app.service.impl.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {LoginServiceImpl.class})
@ExtendWith(MockitoExtension.class)
class LoginServiceImplUnitTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private BlacklistService blacklistService;

    @Mock
    private BlacklistedTokenRepository blackListedTokenRepository;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private BlacklistedToken blacklistToken;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    private LoginServiceImpl loginServiceImpl;

    Authentication authentication = Mockito.mock(Authentication.class);

    SecurityContext securityContext = Mockito.mock(SecurityContext.class);

    private CustomUserDetailsService userDetailsService;



    @InjectMocks
    UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder().email("stan@gmail.com").password("1234").build();
    }

    @Test
    void testLogin() throws Exception {
        when(jwtTokenProvider.generateToken(any())).thenReturn("ABC123");

        LoginRequest loginDto = new LoginRequest();
        loginDto.setEmail("stan@gmail.com");
        loginDto.setPassword("1234");
        Authentication auth =  new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),loginDto.getPassword());
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        BaseResponse<JwtAuthResponse> actualLoginResult = loginServiceImpl.login(loginDto);
        Assertions.assertThat(HttpStatus.OK).isEqualTo(actualLoginResult.getStatus());
        assertEquals("Login Successful", actualLoginResult.getMessage());
        assertEquals("ABC123", actualLoginResult.getResult().getAccessToken());
        verify(jwtTokenProvider).generateToken(any());
        verify(httpServletResponse).setHeader(anyString(), anyString());
        verify(authenticationManager).authenticate(any());

    }


    @Test
    void testLogout() {
        when(this.httpServletRequest.getHeader((String) any())).thenReturn("Bearer jnskjnkednkjdnkljvnkdjnvkjdnskfjvklsd");
        var actualResponse = loginServiceImpl.logout();
        Assertions.assertThat(actualResponse.getMessage()).isEqualTo("Logout Successful");
        Assertions.assertThat(actualResponse.getStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void resetPassword(){
        PasswordRequest resetPasswordDto = new PasswordRequest("stan@gmail.com", "1234","stan", "stan");
        Mockito.when(jwtTokenProvider.getUsernameFromJwt(any())).thenReturn(user.getEmail());
        Mockito.when(userRepository.findUserByEmail(any())).thenReturn(user);
        String newPassword = "iou23iu23ioy3o73873ii";
        Mockito.when(passwordEncoder.encode(any())).thenReturn(newPassword);
        Mockito.when(userRepository.save(any())).thenReturn(user);
        BaseResponse<String> resetPassword = loginServiceImpl.resetPassword(resetPasswordDto, "hsdjksuiwue");
        Assertions.assertThat(resetPassword.getMessage()).isEqualTo("Password Reset Successfully");
        Assertions.assertThat(resetPassword.getStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testChangePasswordSuccessful(){
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(user.getEmail());
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(user);
        PasswordRequest passwordRequest = new PasswordRequest("stan@gmail.com", "1234","stan", "stan");

        when(passwordEncoder.matches(passwordRequest.getOldPassword(), user.getPassword())).thenReturn(true);
        Assertions.assertThat(loginServiceImpl.changePassword(passwordRequest)
                .getStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testForIncorrectOldPassword() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(user.getEmail());
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(user);
        PasswordRequest passwordRequest = new PasswordRequest("stan@gmail.com", "1234","stan", "stan");
        when(passwordEncoder.matches(passwordRequest.getOldPassword(), user.getPassword())).thenReturn(false);
        Assertions.assertThat(loginServiceImpl.changePassword(passwordRequest).getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testForNewPasswordAndConfirmPasswordMismatch() {
        PasswordRequest passwordRequest = new PasswordRequest("stan@gmail.com", "1234","stan", "stann");
        Assertions.assertThat(loginServiceImpl.changePassword(passwordRequest).getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testForUserNotLoggedIn() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(user.getEmail());
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(null);
        PasswordRequest passwordRequest = new PasswordRequest("stan@gmail.com", "1234","stan", "stan");
        Assertions.assertThat(loginServiceImpl.changePassword(passwordRequest).getStatus())
                .isEqualTo(HttpStatus.UNAUTHORIZED);
    }


}
