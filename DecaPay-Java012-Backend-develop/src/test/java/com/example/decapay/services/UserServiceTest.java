package com.example.decapay.services;

import com.example.decapay.configurations.mails.EmailSenderService;
import com.example.decapay.exceptions.UserNotFoundException;
import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.UserUpdateRequest;
import com.example.decapay.pojos.responseDtos.LoginResponseDto;
import com.example.decapay.pojos.responseDtos.UpdateProfileResponseDto;
import com.example.decapay.repositories.TokenRepository;
import com.example.decapay.repositories.UserRepository;
import com.example.decapay.services.impl.UserServiceImpl;
import com.example.decapay.utils.CloudinaryUtils;
import com.example.decapay.utils.MailSenderUtil;
import com.example.decapay.utils.UserIdUtil;
import com.example.decapay.utils.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private MailSenderUtil mailSenderUtil;
    @Mock
    private UserIdUtil userIdUtil;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserUtil userUtil;
    private UserUpdateRequest userUpdateRequest;

    @Mock
    private  PasswordEncoder passwordEncoder;
    @Mock
    private  EmailSenderService emailSenderService;
    @Mock
    private  TokenRepository tokenRepository;
    @Mock
    private CloudinaryUtils cloudinaryUtils;

    @BeforeEach
    void setUp() {

        userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setFirstName("Mic");
        userUpdateRequest.setLastName("Aj");
        userUpdateRequest.setPhoneNumber("08022222222");
    }

    @Test    void editUser() {
        User user = new User();
        user.setFirstName("Mic");
        user.setLastName("Aj");
        user.setPhoneNumber("08022222222");

        String email = "mic@gmail.com";

        given(userUtil.getAuthenticatedUserEmail()).willReturn(email);
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
        given(userRepository.save(any(User.class))).willReturn(user);

        ResponseEntity<LoginResponseDto> loginResponseDtoResponse = userService.editUser(userUpdateRequest);
        assertNotNull(loginResponseDtoResponse);
        verify(userRepository).save(user);
    }

    @Test
    public void testUploadProfilePicture() throws IOException, UserNotFoundException {
        // Prepare test data
        MultipartFile image = Mockito.mock(MultipartFile.class);
        String email = "cheesezzy@gmail.com";
        User user = new User();
        user.setEmail(email);
        String pictureUrl = "http://test.com/image.jpg";

        when(userUtil.getAuthenticatedUserEmail()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(cloudinaryUtils.createOrUpdateImage(image, user)).thenReturn(pictureUrl);



        ResponseEntity result = userService.uploadProfilePicture(image);
        assertEquals(ResponseEntity.ok(UpdateProfileResponseDto.builder().imageUrl(pictureUrl).build()), result);
        assertEquals(pictureUrl, user.getImagePath());

        verify(userRepository).save(user);
    }

}