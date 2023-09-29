package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.UpdatePasswordDto;
import com.decagon.OakLandv1be.entities.Person;
import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.enums.Role;
import com.decagon.OakLandv1be.exceptions.PasswordMisMatchException;
import com.decagon.OakLandv1be.exceptions.UserNotFoundException;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.services.PersonService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import com.decagon.OakLandv1be.utils.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class AuthenticationControllerTest {

    @Autowired
    private PersonService personService;

    @MockBean
    private PersonRepository personRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    Person person;

    UpdatePasswordDto updatePasswordDto;

    @BeforeEach
    void setUp() {
        person = Person.builder()
                .firstName("Benson")
                .lastName("Malik")
                .email("benson@gmail.com")
                .gender(Gender.MALE)
                .date_of_birth("13-08-1990")
                .phone("9859595959")
                .verificationStatus(true)
                .password(passwordEncoder.encode("password123"))
                .address("No Address")
                .role(Role.ADMIN)
                .build();
        person.setId(1L);

        updatePasswordDto =
                new UpdatePasswordDto("password123", "wordpass321","wordpass321");
    }

    @Test
    void updatePassword() {
        when(personRepository.save(person)).thenReturn(person);
        assertEquals(person , personRepository.save(person));
        when(personRepository.findByEmail(any())).thenReturn(Optional.ofNullable(person));

        ApiResponse<String> expectedApiResponse =
                new ApiResponse<>("Password updated successfully", true,null,HttpStatus.OK);

        try (MockedStatic<UserUtil> mockStatic = mockStatic(UserUtil.class)) {

            mockStatic.when(UserUtil::extractEmailFromPrincipal)
                    .thenReturn(Optional.of ("benny@gmail.com"));

            assertEquals(Optional.of("benny@gmail.com"), UserUtil.extractEmailFromPrincipal());
            mockStatic.verify(UserUtil::extractEmailFromPrincipal);

            ApiResponse<String> apiResponse1 =
                    personService.updatePassword( updatePasswordDto);

            assertEquals(expectedApiResponse.toString(), apiResponse1.toString());
        }
    }


    @Test
    void shouldThrowUserNotFoundException() {
        when(personRepository.save(person)).thenReturn(person);
        assertEquals(person, personRepository.save(person));
        try (MockedStatic<UserUtil> mockStatic = mockStatic(UserUtil.class)) {

            mockStatic.when(UserUtil::extractEmailFromPrincipal)
                    .thenReturn(Optional.of("benny@gmail.com"));

            assertEquals(Optional.of("benny@gmail.com"), UserUtil.extractEmailFromPrincipal());
            mockStatic.verify(UserUtil::extractEmailFromPrincipal);

            when(personRepository.findByEmail(any())).thenReturn(Optional.empty());
            assertThrows(UserNotFoundException.class, () ->
                    personService.updatePassword(updatePasswordDto));
        }
    }
    @Test
    void shouldThrowPasswordMisMatchException() {
        when(personRepository.save(person)).thenReturn(person);
        assertEquals(person, personRepository.save(person));
        try (MockedStatic<UserUtil> mockStatic = mockStatic(UserUtil.class)) {

            mockStatic.when(UserUtil::extractEmailFromPrincipal)
                    .thenReturn(Optional.of("benny@gmail.com"));

            assertEquals(Optional.of("benny@gmail.com"), UserUtil.extractEmailFromPrincipal());
            mockStatic.verify(UserUtil::extractEmailFromPrincipal);
            when(personRepository.findByEmail(any())).thenReturn(Optional.ofNullable(person));
            updatePasswordDto.setOldPassword("oldPassword");
            assertThrows(PasswordMisMatchException.class, () ->
                    personService.updatePassword(updatePasswordDto));
        }
    }}

