package com.example.decapay.controllers;

import com.example.decapay.configurations.security.CustomUserDetailService;
import com.example.decapay.configurations.security.JwtAuthFilter;
import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.UserUpdateRequest;
import com.example.decapay.pojos.responseDtos.LoginResponseDto;
import com.example.decapay.repositories.UserRepository;
import com.example.decapay.services.UserService;
import com.example.decapay.utils.UserUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers=UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Mock
    private UserUtil userUtil;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CustomUserDetailService customUserDetailService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @Test
    void editUser() throws Exception {
        User user = new User();
        UserUpdateRequest updateRequest = new UserUpdateRequest();
        updateRequest.setFirstName("Michael1");
        updateRequest.setLastName("Ajay");
        updateRequest.setPhoneNumber("08062456738");

        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .lastName(updateRequest.getLastName())
                .firstName(updateRequest.getFirstName())
                .phoneNumber(updateRequest.getPhoneNumber())
                .build();

        String email = "mic@gmail.com";

        given(userUtil.getAuthenticatedUserEmail()).willReturn(email);
        given (userRepository.findByEmail(anyString())).willReturn(Optional.of(user));
        given(userService.editUser(updateRequest)).willReturn(ResponseEntity.ok(loginResponseDto));

        String requestBody = mapper.writeValueAsString(updateRequest);
        mockMvc.perform(put("/api/v1/user/edit/")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk());
    }
}