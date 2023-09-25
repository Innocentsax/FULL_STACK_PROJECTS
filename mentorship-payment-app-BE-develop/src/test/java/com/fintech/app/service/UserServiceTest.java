package com.fintech.app.service;

import com.fintech.app.model.User;
import com.fintech.app.model.VerificationToken;
import com.fintech.app.model.Wallet;
import com.fintech.app.repository.UserRepository;
import com.fintech.app.repository.VerificationTokenRepository;
import com.fintech.app.repository.WalletRepository;
import com.fintech.app.request.UserRequest;
import com.fintech.app.response.BaseResponse;
import com.fintech.app.response.UserResponse;
import com.fintech.app.service.impl.UserServiceImpl;
import com.fintech.app.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest{

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private Util util;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    VerificationTokenRepository verificationTokenRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private WalletService walletService;

    @Mock
    private ApplicationEventPublisher publisher;



    private UserRequest userRequest;
    private UserResponse userResponse;
    private User user;
    private Wallet wallet;


    @BeforeEach
    void setUp() {
         userRequest = UserRequest.builder()
                .firstName("Fintech user")
                .lastName("Fintech user")
                .email("fintech@gmail.com")
                .phoneNumber("1234567898")
                .bvn("123456799")
                .password("password")
                .confirmPassword("password")
                .build();

         userResponse = UserResponse.builder()
                .firstName("Fintech user")
                .lastName("Fintech user")
                .email("fintech@gmail.com")
                .phoneNumber("1234567898")
                .bvn("123456799")
                .token("userregistraiontokem")
                .build();

         user = User.builder()
                .id(1L)
                .firstName("Fintech user")
                .lastName("Fintech user")
                .email("fintech@gmail.com")
                .phoneNumber("1234567898")
                .bvn("123456799")
                .password("password")
                .build();

         wallet = Wallet.builder()
                .bankName("fintech bank")
                .balance(1000.0)
                .build();


    }

    @Test
    @DisplayName("TEST: Create User account")
    void should_createUserAccount() throws JSONException {
        when(userRepository.existsByEmail("fintech@gmail.com")).thenReturn(false);
        when(passwordEncoder.encode(userRequest.getPassword())).thenReturn("password");
        when(util.validatePassword(user.getPassword(), user.getPassword())).thenReturn(true);
        when(util.requestToUser(userRequest)).thenReturn(user);
        when(util.userToResponse(user)).thenReturn(userResponse);
        when(walletRepository.save(any())).thenReturn(wallet);
        when(walletService.createWallet(any())).thenReturn(wallet);
        doNothing().when(publisher).publishEvent(any());
        given(userRepository.save(any())).willReturn(user);

        BaseResponse<UserResponse> userAccount = userService.createUserAccount(userRequest, request);
        assertThat(userAccount).isNotNull();
        assertThat(userAccount.getResult()).isNotNull();
        assertThat(userAccount.getResult().getEmail()).isEqualTo(user.getEmail());
        assertThat(userAccount.getMessage()).isEqualTo("Registration success");
        assertThat(userAccount.getStatus()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("TEST: get user profile")
    void should_getUser() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(user.getEmail());
        when(userRepository.findUserByEmail(any())).thenReturn(user);
        when(util.userToResponse(user)).thenReturn(userResponse);

        BaseResponse<UserResponse> returnUser = userService.getUser();
        assertThat(returnUser).isNotNull();
        assertThat(returnUser.getResult()).isNotNull();
        assertThat(returnUser.getResult().getEmail()).isEqualTo(user.getEmail());
        assertThat(returnUser.getMessage()).isEqualTo("user profile");
        assertThat(returnUser.getStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("TEST: save verification code")
    void should_saveVerificationTokenForUser() {
        when(verificationTokenRepository.save(any()))
                .thenReturn(new VerificationToken("token", user));
        userService.saveVerificationTokenForUser("token", user);
    }
}