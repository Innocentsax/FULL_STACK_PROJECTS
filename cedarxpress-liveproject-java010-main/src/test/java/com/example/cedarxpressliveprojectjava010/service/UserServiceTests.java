package com.example.cedarxpressliveprojectjava010.service;

import com.example.cedarxpressliveprojectjava010.dto.EditUserDetailsDto;
import com.example.cedarxpressliveprojectjava010.dto.RegistrationDto;
import com.example.cedarxpressliveprojectjava010.dto.UpdatePasswordDto;
import com.example.cedarxpressliveprojectjava010.entity.User;
import com.example.cedarxpressliveprojectjava010.enums.Role;
import com.example.cedarxpressliveprojectjava010.exception.BadCredentialsException;
import com.example.cedarxpressliveprojectjava010.enums.Gender;
import com.example.cedarxpressliveprojectjava010.repository.UserRepository;
import com.example.cedarxpressliveprojectjava010.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private ModelMapper mapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    private EditUserDetailsDto editUserDetailsDto;

    RegistrationDto registrationDto;
    User user;

    Authentication auth = Mockito.mock(Authentication.class);

    @BeforeEach
    public void setup() {
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        editUserDetailsDto = EditUserDetailsDto.builder()
                .firstName("First")
                .lastName("Success")
                .gender(Gender.MALE)
                .dob(LocalDate.now())
                .build();

        registrationDto = RegistrationDto.builder()
                .firstName("first")
                .lastName("last")
                .email("email")
                .password("1234")
                .confirmPassword("1234")
                .build();

        user = new User();
        user.setId(1L);
        user.setEmail("email");
        user.setPassword("1234");
        user.setLastName("last");
        user.setRole(Role.ROLE_CUSTOMER);
        user.setFirstName("first");

    }

    @Test
    public void givenUserObject_whenCreatUser_thenSaveUser() {


        given(userRepository.existsByEmail("email")).willReturn(false);
        given(passwordEncoder.encode(registrationDto.getPassword())).willReturn("1234");
        given(mapper.map(registrationDto, User.class)).willReturn(user);
        given(userRepository.save(any())).willReturn(user);
        given(mapper.map(user, RegistrationDto.class)).willReturn(registrationDto);

        ResponseEntity<RegistrationDto> response = userService.registerUser(registrationDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(registrationDto);
    }

    @Test
    public void givenUserObject_whenCreatUser_thenThrowException() {
        given(userRepository.existsByEmail("email")).willReturn(true);

        Throwable thrown = catchThrowable(() -> userService.registerUser(registrationDto));

        assertThat(thrown).isInstanceOf(RuntimeException.class)
                .hasMessage("User already exist");
    }

    @Test
    public void givenUserObject_whenPasswordsDoNotMatch_thenThrowException() {
        registrationDto.setConfirmPassword("anotherString");
        Throwable thrown = catchThrowable(() -> userService.registerUser(registrationDto));
        assertThat(thrown).isInstanceOf(RuntimeException.class)
                .hasMessage("Passwords do not match");
    }

    @DisplayName("Test for different values for ConfirmPassword and New Password")
    @Test
    public void shouldThrowBadCredentialExceptionForInconsistentNewPassword() {
        UpdatePasswordDto updatePasswordDto = UpdatePasswordDto.builder()
                .newPassword("password").confirmPassword("inconsistent").oldPassword("12345")
                .build();
        Throwable thrown = catchThrowable(() -> userService.updatePassword(updatePasswordDto));

        assertThat(thrown).isInstanceOf(BadCredentialsException.class).hasMessage("New password don't match!");
    }

    @DisplayName("Test for Same values for Old and New Password")
    @Test
    public void shouldThrowBadCredentialExceptionForSameOldAndNewPasswordValues() {
        UpdatePasswordDto updatePasswordDto = UpdatePasswordDto.builder()
                .newPassword("password").confirmPassword("password").oldPassword("password")
                .build();
        Throwable thrown = catchThrowable(() -> userService.updatePassword(updatePasswordDto));

        assertThat(thrown).isInstanceOf(BadCredentialsException.class).hasMessage("New password can't be same with old!");
    }

    @DisplayName("Test for Same values for Old inputted By User and old password in database")
    @Test
    public void shouldThrowBadCredentialExceptionForDifferentOldPasswordValues() {
        UpdatePasswordDto updatePasswordDto = UpdatePasswordDto.builder()
                .newPassword("12345").confirmPassword("12345").oldPassword("password")
                .build();
        String email = "damilola@gmail.com";
        User user = User.builder()
                .firstName("Damilola")
                .lastName("Oluwole")
                .password("encrypted%%%%")
                .build();


        given(auth.getName()).willReturn("damilola@gmail.com");
        given(userRepository.getUsersByEmail(email)).willReturn(user);
        given(passwordEncoder.matches(updatePasswordDto.getOldPassword(), user.getPassword())).willReturn(false);

        Throwable thrown = catchThrowable(() -> userService.updatePassword(updatePasswordDto));

        assertThat(thrown).isInstanceOf(BadCredentialsException.class).hasMessage("Old password incorrect!");
    }

    @DisplayName("Test for Successfully Updating password")
    @Test
    public void shouldThrowCallNecessaryMethods() {
        UpdatePasswordDto updatePasswordDto = UpdatePasswordDto.builder()
                .newPassword("12345").confirmPassword("12345").oldPassword("password")
                .build();
        String email = "damilola@gmail.com";
        User user = User.builder()
                .firstName("Damilola")
                .lastName("Oluwole")
                .password("encrypted%%%%")
                .build();


        given(auth.getName()).willReturn("damilola@gmail.com");
        given(userRepository.getUsersByEmail(email)).willReturn(user);
        given(passwordEncoder.matches(updatePasswordDto.getOldPassword(), user.getPassword())).willReturn(true);

        var response = userService.updatePassword(updatePasswordDto);

        verify(userRepository, times(1)).save(user);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(response.getBody()).isEqualTo("Password updated!");

    }

    @DisplayName("Test for LoadUSerByUserName throwing error")
    @Test
    public void shouldThrowErrorIfUserNotFound() {
        given(userRepository.findUserByEmail("damilola@gmail.com")).willReturn(Optional.empty());
        Throwable thrown = catchThrowable(() -> userService.loadUserByUsername("damilola@gmail.com"));
        assertThat(thrown).isInstanceOf(UsernameNotFoundException.class).
                hasMessage("User with email damilola@gmail.com not found");
    }

    @DisplayName("Test for LoadUSerByUserName should return Userdetails")
    @Test
    public void shouldThrowFindUserANdReturnUserDetails() {
        given(userRepository.findUserByEmail("email")).willReturn(Optional.of(user));
        UserDetails response = userService.loadUserByUsername("email");

        assertThat(response).isInstanceOf(org.springframework.security.core.userdetails.User.class);
        assertThat(response.getUsername()).isEqualTo("email");

    }
    @DisplayName("Unit test for the edit user details method")
    @Test
    public void shouldCallTheNecessaryMethods () {
        String name = "email";
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(name);
        given(userRepository.getUserByEmail(name)).willReturn(user);

        userService.editUserDetails(editUserDetailsDto);

        assertEquals(editUserDetailsDto.getLastName(), user.getLastName());
    }
}
