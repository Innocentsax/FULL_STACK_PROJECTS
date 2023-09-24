package com.decagon.kindredhairapigrp1.controllers;

import com.decagon.kindredhairapigrp1.DTO.AnswerResponseDTO;
import com.decagon.kindredhairapigrp1.DTO.AuthenticationRequest;
import com.decagon.kindredhairapigrp1.DTO.ProductDTO;
import com.decagon.kindredhairapigrp1.models.ProductRecommendation;
import com.decagon.kindredhairapigrp1.models.User;
import com.decagon.kindredhairapigrp1.models.UserDetails;
import com.decagon.kindredhairapigrp1.repository.ProductRecommendationRepository;
import com.decagon.kindredhairapigrp1.repository.UserRepository;
import com.decagon.kindredhairapigrp1.services.ProductService;
import com.decagon.kindredhairapigrp1.utils.TestUtils;
import com.decagon.kindredhairapigrp1.utils.api.response.ApiResponse;
import com.decagon.kindredhairapigrp1.utils.security.JwtUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserRepository userRepositoryMock;

    @MockBean
    ProductRecommendationRepository mockedProductRecommendationRepository;

    @MockBean
    ProductService productService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private AuthenticationRequest authenticationRequest = new AuthenticationRequest();

    User user = new User();
    List<ProductDTO> productDTOList;
    String token ;


    @BeforeAll
    public void setUp() {

        try {
            productDTOList = (List<ProductDTO>) TestUtils.deserialize("src/test/java/com/decagon/kindredhairapigrp1/utils/test_product_list.data");
        } catch (Exception e) {
            e.printStackTrace();
            productDTOList =   new ArrayList<>();
        }

        user = new User();
        user.setId(1L);
        user.setEmail("user@app.com");
        user.setRole("USER");
        user.setStatus("");
        user.setPhoneNumber("0909999999");
        user.setEnabled(true);
        user.setPassword(bCryptPasswordEncoder.encode("foo"));

        authenticationRequest.setEmail(user.getEmail());
        authenticationRequest.setPassword("foo");

        token = "Bearer " + jwtUtil.generateLoginToken(user);

        UserDetails userDetails = TestUtils.generateMockUserDetails();
        user.setUserDetails(userDetails);
        Optional<User> optionalUser = Optional.of(user);
        when(userRepositoryMock.findById(1L)).thenReturn(optionalUser);
        when(userRepositoryMock.findByEmail(anyString())).thenReturn(optionalUser);
    }

    @Test
    void getUserProfileDetails() {
    }

    @Test
    void getUserRecommendedProduct() throws Exception{
        token = "Bearer " + jwtUtil.generateLoginToken(user);
        mockMvc.perform(get("/user/{id}/recommendation", user.getId())
                .header("Authorization",  token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print());
    }


    @Test
    void shouldUpdateTheUserProfile_WhenTheEndPointIsCalled() throws Exception {
        //Arrange
        user.setEmail("hello@hello.com");
        user.setId(2L);
        var response = new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("User profile updated");
        List<ProductRecommendation> productRecommendations = new ArrayList<>(TestUtils.generateProductRecommendations());
        token = "Bearer " + jwtUtil.generateLoginToken(user);
        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(user));
        when(mockedProductRecommendationRepository.saveAll(anyCollection())).thenReturn(productRecommendations);
        doNothing().when(mockedProductRecommendationRepository).deleteInBatch(anyCollection());
        when(productService.findAllAvailableProducts()).thenReturn(productDTOList);
        when(userRepositoryMock.findByEmail(anyString())).thenReturn(Optional.of(user));

        AnswerResponseDTO answerResponseDTO = TestUtils.getAnswerResponseDTO();

        //Act and Assert
        mockMvc.perform(put("/user/{id}/profile/update", user.getId())
                .header("Authorization",  token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.convertObjectToJsonBytes(answerResponseDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print());
        verify(userRepositoryMock, times(1)).findByEmail(anyString());
        verify(userRepositoryMock, times(1)).findById(anyLong());
        verify(mockedProductRecommendationRepository, times(1)).saveAll(anyCollection());
        verify(mockedProductRecommendationRepository, times(1)).deleteInBatch(anyCollection());
        verify(productService, times(3)).findAllAvailableProducts();
    }
}