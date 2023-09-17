package com.wakacast.services.service_impl;

import com.wakacast.configurations.security_configs.JwtTokenUtil;
import com.wakacast.dto.ResetPasswordDto;
import com.wakacast.enums.Gender;
import com.wakacast.exceptions.UserWithEmailNotFound;
import com.wakacast.models.User;
import com.wakacast.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ForgetPasswordServiceImplTest {

    private User user;

    @Mock
    private UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private ForgetPasswordServiceImpl forgetPasswordServiceimpl;

    @Test
    void resetPassword() {
        user = new User();
        user.setEmail("user@gmail.com");
        user.setGender(Gender.FEMALE);
        user.setPassword("123456789");
        user.setAccountVerified(true);
        String token = "";

        ResetPasswordDto resetPasswordDto = new ResetPasswordDto();
        resetPasswordDto.setNewPassword("12345678");

        String mail = user.getEmail();

        User userTest = new User();
        userTest.setPassword("12345678");

        when(jwtTokenUtil.getUserEmailFromToken(token)).thenReturn(mail);
        when(userRepository.findUserByEmail(mail)).thenReturn(Optional.empty());

        assertThrows(UserWithEmailNotFound.class, ()-> forgetPasswordServiceimpl.resetPassword(resetPasswordDto,token));
    }
}