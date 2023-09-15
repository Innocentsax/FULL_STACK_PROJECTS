package com.example.cedarxpressliveprojectjava010.service.implementation;

import com.example.cedarxpressliveprojectjava010.config.jwt.JwtTokenProvider;
import com.example.cedarxpressliveprojectjava010.dto.request.ForgotPasswordRequest;
import com.example.cedarxpressliveprojectjava010.dto.request.ResetPasswordRequest;
import com.example.cedarxpressliveprojectjava010.dto.response.MessageResponse;
import com.example.cedarxpressliveprojectjava010.entity.User;
import com.example.cedarxpressliveprojectjava010.exception.NotFoundException;
import com.example.cedarxpressliveprojectjava010.repository.UserRepository;
import com.example.cedarxpressliveprojectjava010.service.EmailSenderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.mail.MessagingException;
import java.util.Collections;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserManagementServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private EmailSenderService emailSenderService;

    @InjectMocks
    private UserManagementServiceImpl userManagementService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("johndoe@gmail.com");
        user.setPassword("hello");
    }

    @Test
    void whenValidUserForgetsPassword_ShouldSendResetPasswordLink() throws MessagingException {
        String jwtToken = "bejbvjubovjebovjeboj";
        //User user = new User();
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                null,
                Collections.singleton(new SimpleGrantedAuthority(String.valueOf(user.getRole())))
        );


        ForgotPasswordRequest forgotPasswordRequest =
                new ForgotPasswordRequest(
                        "johndoe@gmail.com"
                );
        when(userRepository.findUserByEmail(forgotPasswordRequest.getEmail())).
                thenReturn(Optional.of(user));

        when(jwtTokenProvider.generateToken(authentication)).thenReturn(jwtToken);
        when(emailSenderService.send(any())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        ResponseEntity<MessageResponse> result = userManagementService.forgotPassword(forgotPasswordRequest);

        assertThat(result.getBody().getMessage()).isEqualTo("Kindly check email for reset Link!");
        assertThat(forgotPasswordRequest.getEmail()).isEqualTo(user.getEmail());
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    void whenUserInputsWrongEmail_ShouldThrowNotFoundException(){
        ForgotPasswordRequest forgotPasswordRequest =
                new ForgotPasswordRequest(
                        "ebube@gmail.com"
                );
        when(userRepository.findUserByEmail(forgotPasswordRequest.getEmail())).thenReturn(Optional.ofNullable(null));
        assertThatThrownBy(() -> {
            userManagementService.forgotPassword(forgotPasswordRequest);
        })
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(String.format("User with email %s doesn't exist!", forgotPasswordRequest.getEmail()));
    }


    @Test
    void whenUserInputsNewPassword_ShouldReplaceOldPassword() {
        String jwtToken = "";
        ResetPasswordRequest request = new ResetPasswordRequest(
                "johndoe@gmail.com",
                "password",
                "password"
        );
        when(userRepository.findUserByEmail(request.getEmail())).thenReturn(Optional.of(user));

        ResponseEntity<MessageResponse> response = userManagementService.resetPassword(jwtToken, request);

        verify(userRepository, times(1)).save(user);
        verify(passwordEncoder, times(1)).encode(request.getNewPassword());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMessage()).isEqualTo("Password updated successfully");
    }

}