package com.wakacast.services.service_impl;

import com.wakacast.dto.ChangePasswordDto;
import com.wakacast.models.User;
import com.wakacast.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class ChangePasswordImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ModelMapper mapper;
    @InjectMocks
    private ChangePasswordImpl changePassword;


    @Test
    void updatePassword() {
        User user1 = new User();
        user1.setPassword("12345678");
        user1.setEmail("user@gmail.com");
        Optional<User> user = Optional.of(user1);
        lenient().when(userRepository.findUserByEmail("user@gmail.com")).thenReturn(user);
        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        changePasswordDto.setOldPassword(user.get().getPassword());
        changePasswordDto.setNewPassword("87654321");

        String returnedValue = changePassword.updatePassword(changePasswordDto, user1.getEmail());
        assertThat(returnedValue).isEqualTo("Password has been changed successfully");
    }
}