package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.dtos.SignUpDto;
import com.decagon.chompapp.models.Cart;
import com.decagon.chompapp.models.Role;
import com.decagon.chompapp.models.User;
import com.decagon.chompapp.repositories.CartRepository;
import com.decagon.chompapp.repositories.RoleRepository;
import com.decagon.chompapp.repositories.UserRepository;
import com.decagon.chompapp.security.JwtTokenProvider;
import com.decagon.chompapp.services.EmailSenderService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceImplTest {
    @InjectMocks
    private RegistrationServiceImpl registrationService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private EmailSenderService emailSender;
    @Mock
    JwtTokenProvider jwtTokenProvider;

    @Mock
    private HttpServletRequest request;

    private SignUpDto signUpDto;
    private Role role;
    private User user;
    private Cart userCart;


    @BeforeEach
    void setUp() {
        signUpDto = new SignUpDto("Stanley", "Nkannebe",
                "funkystan", "funkystan@gmail.com", "12345");

        request = Mockito.mock(HttpServletRequest.class);
        role = Role.builder().name("ROLE_PREMIUM").build();
        user = User.builder().firstName("Stanley").lastName("Nkannebe").username("funkystan")
                .email("funkystan@gmail.com").password("12345").build();
        userCart = Cart.builder().cartId(1L).cartTotal(0).user(user).quantity(0).cartItemList(new ArrayList<>()).build();


    }

    @Test
    void shouldReturnUserNameAlreadyTakenWhenUserTriesToSignUpWithAlreadyTakenUserName() throws MalformedURLException {
        Mockito.when(userRepository.existsByUsername(signUpDto.getUsername())).thenReturn(true);
        ResponseEntity<String> responseEntity = registrationService.registerUser(signUpDto, request);
        Assertions.assertThat(responseEntity.getBody()).isEqualTo("Username is already taken!");
    }


    @Test
    void shouldReturnEmailAlreadyTakenWhenUserTriesToSignUpWithAlreadyTakenEmail() throws MalformedURLException {
        Mockito.when(userRepository.existsByEmail(signUpDto.getEmail())).thenReturn(true);
        ResponseEntity<String> responseEntity = registrationService.registerUser(signUpDto, request);
        Assertions.assertThat(responseEntity.getBody()).isEqualTo("Email is already taken!");
    }

    @Test
    void userGetsSaved() throws MalformedURLException {
        Mockito.when(userRepository.existsByUsername(signUpDto.getUsername())).thenReturn(false);
        Mockito.when(userRepository.existsByEmail(signUpDto.getEmail())).thenReturn(false);
        Mockito.when(roleRepository.findByName("ROLE_PREMIUM")).thenReturn(Optional.of(role));
        Mockito.when(jwtTokenProvider.generateSignUpConfirmationToken(any())).thenReturn("khvvjbhilhliehwilhewlbjdhbiluhweiuh");
        Mockito.when(userRepository.save(any())).thenReturn(user);
        Mockito.when(cartRepository.save(any())).thenReturn(userCart);
        ResponseEntity<String> responseEntity = registrationService.registerUser(signUpDto, request);
        Assertions.assertThat(responseEntity.getBody()).isEqualTo("User registered successfully. Kindly check your mail inbox or junk folder to verify your account");
    }


    @Test
    void testThatATokenPassedInWillBeConfirmedAndUserWillBeEnabled() {
        Mockito.when(userRepository.findByConfirmationToken(any())).thenReturn(Optional.of(user));
        Mockito.when(jwtTokenProvider.validateToken(any())).thenReturn(true);
        ResponseEntity<String> responseEntity = registrationService.confirmRegistration("lGHjhgJHGjhgKui67857657igU67R76");
        Assertions.assertThat(user.getIsEnabled()).isEqualTo(true);
        Assertions.assertThat(responseEntity.getBody()).isEqualTo("Account verification successful");
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }

    @Test
    void verifyRegistration() throws MalformedURLException {
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(user));
        Mockito.when(jwtTokenProvider.generateSignUpConfirmationToken(any())).thenReturn("khvvjbhilhliehwilhewlbjdhbiluhweiuh");
        ResponseEntity<String> responseEntity = registrationService.verifyRegistration(1);
        Assertions.assertThat(responseEntity.getBody()).isEqualTo("Kindly check your mail inbox or junk folder to verify your account");
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);


    }

}