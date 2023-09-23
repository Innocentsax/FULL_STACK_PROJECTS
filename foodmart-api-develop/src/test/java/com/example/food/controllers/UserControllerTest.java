package com.example.food.controllers;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.configurations.security.CustomUserDetailsService;
import com.example.food.configurations.security.JwtAuthFilter;
import com.example.food.dto.EditUserDto;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.time.LocalDate;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testEditUserDetails() throws Exception {
       EditUserDto editUserDto = new EditUserDto();
       editUserDto.setFirstName("Gisung");
       editUserDto.setLastName("Kefas");
       editUserDto.setEmail("kefas@gmail.com");
       editUserDto.setDateOfBirth(new Date(07-02-2020));

       String requestBody = mapper.writeValueAsString(editUserDto);

       when(userService.editUserDetails(any(EditUserDto.class))).thenReturn(new BaseResponse(ResponseCodeEnum.SUCCESS));

       mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/auth/edit-user")
                       .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(requestBody))
                        .andDo(print())
                    .andExpect(status().isAccepted());
    }

}