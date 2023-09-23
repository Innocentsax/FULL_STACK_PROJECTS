package com.example.food.service.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.configurations.security.CustomUserDetailsService;
import com.example.food.configurations.security.JwtUtil;
import com.example.food.dto.*;
import com.example.food.exceptions.UserNotFoundException;
import com.example.food.model.Cart;
import com.example.food.model.Users;
import com.example.food.model.Wallet;
import com.example.food.pojos.CreateUserRequest;
import com.example.food.pojos.LoginResponseDto;
import com.example.food.repositories.CartRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.repositories.WalletRepository;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.EmailService;
import com.example.food.services.serviceImpl.UserServiceImpl;
import com.example.food.util.AppUtil;
import com.example.food.util.ResponseCodeUtil;
import com.example.food.util.UserUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private AppUtil appUtil;

    @Mock
    private UserUtil userUtil;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private UserDetails userDetails;


    @Mock
    private Authentication authentication;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    UserRepository userRepository;
    @Mock
    WalletRepository walletRepository;
    @Mock
    CartRepository cartRepository;
    @Mock
    private EmailService emailService;
    @Mock
    EmailSenderDto emailSenderDto;
    @InjectMocks
    private UserServiceImpl userServiceImpl;
    private ResponseCodeUtil responseCodeUtil;
    private CreateUserRequest createUserRequest;
    private Users users;
    private Cart cart;
    LoginRequestDto loginRequestDto;
    ConfirmRegistrationRequestDto confirmRegistrationRequestDto;
    Wallet wallet;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("opeyemialbert20@gmail.com");
        loginRequestDto.setPassword("12345");

        createUserRequest = CreateUserRequest.builder()
                .firstName("Akeem")
                .lastName("Jaiyeade")
                .email("haykay364@gmail.com")
                .password("1234")
                .confirmPassword("1234")
                .build();
        users = new Users();
        users.setFirstName(createUserRequest.getFirstName());
        users.setLastName(createUserRequest.getLastName());
        users.setEmail(createUserRequest.getEmail());
        users.setBaseCurrency("USD");
        users.setDateOfBirth(new Date(2000, 1, 1));
        users.setCreatedAt(new Date());
        users.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        users.setConfirmationToken("adsfshdgfgkgkgjgkggk");

        cart = new Cart();
        cart.setUsers(users);

        responseCodeUtil = new ResponseCodeUtil();
        confirmRegistrationRequestDto = new ConfirmRegistrationRequestDto();
        confirmRegistrationRequestDto.setToken(users.getConfirmationToken());
        wallet = Wallet.builder()
                .user(users)
                .walletBalance(BigDecimal.valueOf(0)).build();
    }

    @Test
    public void shouldReturnInvalidEmailAddressWhenUserTriesToRegisterWithInvalidEmail() {
        when(appUtil.validEmail(createUserRequest.getEmail())).thenReturn(false);
        BaseResponse baseResponse = userServiceImpl.signUp(createUserRequest);
        Assertions.assertThat(responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.ERROR_EMAIL_INVALID)
                .getDescription()).isEqualTo("Invalid email address.");
    }

    @Test
    public void shouldReturnUserAlreadyExistWhenUserTriesToSignUpWithAlreadyRegisteredEmail() {
        when(userRepository.existsByEmail(createUserRequest.getEmail())).thenReturn(true);
        BaseResponse baseResponse = userServiceImpl.signUp(createUserRequest);
        Assertions.assertThat(responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.ERROR_DUPLICATE_USER)
                .getDescription()).isEqualTo("User already exist.");
    }

    @Test
    public void signUp() {
        when(appUtil.validEmail(createUserRequest.getEmail())).thenReturn(true);
        when(userRepository.existsByEmail(createUserRequest.getEmail())).thenReturn(false);
        when(cartRepository.save(cart)).thenReturn(cart);
        when(walletRepository.save(wallet)).thenReturn(wallet);
        doNothing().when(emailService).sendMail(isA(EmailSenderDto.class));
        emailService.sendMail(emailSenderDto);
        verify(emailService, times(1)).sendMail(emailSenderDto);
        BaseResponse baseResponse = userServiceImpl.signUp(createUserRequest);
        Assertions.assertThat(baseResponse.getDescription())
                .isEqualTo("Check your email for verification link to validate your account");
    }

    @Test
    public void confirmRegistration() {
        when(userRepository.findByConfirmationToken(confirmRegistrationRequestDto.getToken())).thenReturn(Optional.of(users));
        when(userRepository.save(users)).thenReturn(users);
        when(walletRepository.save(wallet)).thenReturn(wallet);
        BaseResponse baseResponse = userServiceImpl.confirmRegistration(confirmRegistrationRequestDto);
        Assertions.assertThat(baseResponse.getDescription()).isEqualTo("Account verification successful");
    }

    @Test
    public void updatePassword_OldPasswordNotMatch() {
        ChangePasswordDto passwordDto = new ChangePasswordDto();
        passwordDto.setOldPassword("oldpassword");
        passwordDto.setNewPassword("newpassword");
        passwordDto.setConfirmPassword("newpassword");

        Users mockUser = new Users();
        mockUser.setEmail("user@example.com");
        mockUser.setPassword(passwordEncoder.encode("wrongpassword"));

        when(userUtil.getAuthenticatedUserEmail()).thenReturn("user@example.com");
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(mockUser));

        BaseResponse response = userServiceImpl.updatePassword(passwordDto);

        assertEquals(response.getCode(), ResponseCodeEnum.ERROR.getCode());
        assertEquals(response.getDescription(), "Old password does not match");
        verify(userRepository, times(0)).save(mockUser);
    }

    @Test
    public void testGetUserDetails() {

        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(users));


        UserDetailsDto result = userServiceImpl.getUserDetails(userId);


        assertEquals("Akeem", result.getFirstName());
        assertEquals("Jaiyeade", result.getLastName());
        assertEquals("haykay364@gmail.com", result.getEmail());
        assertEquals("USD", result.getBaseCurrency());
        assertEquals(new Date(2000, 1, 1), result.getDateOfBirth());
        assertNotNull(result.getCreatedAt());
//
    }
    @Test
    public void shouldThrowExceptionIfUserIsNotFound() {

        Long userId = 2L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());


        assertThrows(UserNotFoundException.class, () -> userServiceImpl.getUserDetails(userId));
    }

}

