package com.wakacast.services.service_impl;

import com.wakacast.enums.Gender;
import com.wakacast.models.User;
import com.wakacast.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static com.wakacast.enums.UserType.UNSUBSCRIBED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtUserDetailsServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private JwtUserDetailsService jwtUserDetailsService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("og@gmail.com");
        user.setGender(Gender.MALE);
        user.setPassword("12345678");
        user.setAccountVerified(true);
        user.setUserType(UNSUBSCRIBED);
        when(userRepository.findUserByEmail("og@gmail.com")).thenReturn(Optional.of(user));
    }

    @Test
    void loadUserByUsername() {
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername("og@gmail.com");
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(user.getEmail());
    }
}