package com.example.cedarxpressliveprojectjava010.service.implementation;

import com.example.cedarxpressliveprojectjava010.config.jwt.JWTAuthenticationFilter;
import com.example.cedarxpressliveprojectjava010.config.jwt.JwtAuthenticationEntryPoint;
import com.example.cedarxpressliveprojectjava010.controller.UserController;
import com.example.cedarxpressliveprojectjava010.dto.EditUserDetailsDto;
import com.example.cedarxpressliveprojectjava010.entity.User;
import com.example.cedarxpressliveprojectjava010.enums.Gender;
import com.example.cedarxpressliveprojectjava010.repository.UserRepository;
import com.example.cedarxpressliveprojectjava010.service.AddressService;
import com.example.cedarxpressliveprojectjava010.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = UserController.class)
@ExtendWith(SpringExtension.class)
class UserServiceIntegrationTest {

    @Autowired
    private UserController userController;
    @MockBean
    private AddressService addressService;
    @Mock
    private UserServiceImpl userService;

    @MockBean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @MockBean
    PasswordEncoder passwordEncoder;
    @MockBean
    UserDetailsService userDetailsService;
    @MockBean
    UserService userServ;
    @MockBean
    JWTAuthenticationFilter jwtAuthenticationFilter;
    @Mock
    UserRepository userRepository;

    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);

    @Test
    void editUserDetails() throws Exception {
        String message = "User Details edit successful";
        String person = "johnD@gmail.com";
        ResponseEntity<String> response = new ResponseEntity<>(message, HttpStatus.OK);
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .gender(Gender.MALE)
                .password("12345")
                .email("johnD@gmail.com")
                .build();
        EditUserDetailsDto userDto = EditUserDetailsDto.builder()
                .firstName("Johnny")
                .lastName("Daniels")
                .build();
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(person);
        when(userRepository.findUserByEmail(person)).thenReturn(Optional.of(user));
        doNothing().when(userService).editUserDetails(any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDto));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk());
    }
}