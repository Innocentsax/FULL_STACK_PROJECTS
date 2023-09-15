package com.example.cedarxpressliveprojectjava010.controller;

import com.example.cedarxpressliveprojectjava010.dto.UpdatePasswordDto;
import com.example.cedarxpressliveprojectjava010.service.AddressService;
import com.example.cedarxpressliveprojectjava010.service.implementation.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.BDDMockito.given;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerINTTest {
    @Autowired
    private UserController userController;
    @MockBean
    private UserServiceImpl userService;

    @MockBean  private AddressService addressService;

    private UpdatePasswordDto updatePasswordDto;
    @BeforeEach
    public void setUp() {
        updatePasswordDto = UpdatePasswordDto.builder()
                .oldPassword("12345")
                .newPassword("54321")
                .confirmPassword("54321")
                .build();
    }

    @DisplayName("Integration Test for update")
    @Test
    public void logInTest() throws Exception {
        given(userService.updatePassword(updatePasswordDto)).willReturn(new ResponseEntity<>("Password updated!", HttpStatus.ACCEPTED));
        String content = (new ObjectMapper()).writeValueAsString(updatePasswordDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users/")
                .contentType(MediaType.APPLICATION_JSON).content(content);

       ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                                 .build()
                                 .perform(requestBuilder);
             actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
          }
}