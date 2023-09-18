package com.example.decapay.services.impl;

import com.example.decapay.configurations.security.CustomUserDetailService;
import com.example.decapay.configurations.security.JwtUtils;
import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.LoginRequestDto;
import com.example.decapay.pojos.responseDtos.LoginResponseDto;
import com.example.decapay.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private CustomUserDetailService customUserDetailService;

    @Mock
    private UserDetails userDetails;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private Authentication authentication;

    @Mock
    private UserRepository userRepository;



    LoginRequestDto loginRequestDto;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("oluseun@gmail.com");
        loginRequestDto.setPassword("oluseun1");
    }

    @Test
    public void userLogin() {
        User user = new User();
        user.setFirstName("UserOne");
        user.setLastName("Testing");
        user.setUserId("wqertyuiopiuytrffyui");
        user.setImagePath("www.cloudinary.com/image");
        user.setEmail("oluseun@gmail.com");
        user.setPhoneNumber("154-753-875");

        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())))
                .thenReturn(authentication);
        when(customUserDetailService.loadUserByUsername(anyString()))
                .thenReturn(userDetails);
        when(jwtUtils.generateToken(any(UserDetails.class))).thenReturn("934859hfdjghdhfk");
        when(userDetails.getUsername()).thenReturn("test@example.com");
        when(userRepository.findByEmail("oluseun@gmail.com")).thenReturn(Optional.of(user));


        ResponseEntity<LoginResponseDto> response = userService.userLogin(loginRequestDto);

        assertNotNull(response);
        verify(customUserDetailService, times(1))
                .loadUserByUsername(anyString());
    }

}
