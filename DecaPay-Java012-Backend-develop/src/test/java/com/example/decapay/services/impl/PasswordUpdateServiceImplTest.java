package com.example.decapay.services.impl;

import com.example.decapay.configurations.security.JwtUtils;
import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.PasswordUpdateRequest;
import com.example.decapay.repositories.UserRepository;
import com.example.decapay.services.PasswordUpdateService;
import com.example.decapay.utils.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PasswordUpdateServiceImplTest {
    @Autowired
    private PasswordUpdateService passwordUpdateService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserUtil userUtil;

    @BeforeEach
    void setUp() {
        passwordUpdateService = new PasswordUpdateServiceImpl(userRepository, passwordEncoder, userUtil);
    }

    @Test
    void createPassword() {
        PasswordUpdateRequest passwordUpdateRequest = new PasswordUpdateRequest(
                "1234", "123456", "123456");
        User user = new User();
        user.setPassword("1234");
        user.setEmail("jon@email.com");

        given(userUtil.getAuthenticatedUserEmail()).willReturn("jon@email.com");

        given(userRepository.findByEmail("jon@email.com")).willReturn(Optional.of(user));

        given(passwordEncoder.matches(passwordUpdateRequest.getPassword(), user.getPassword())).willReturn(true);

        passwordUpdateService.updatePassword(passwordUpdateRequest);

        verify(userRepository).save(any());
    }
}