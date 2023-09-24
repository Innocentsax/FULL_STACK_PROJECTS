package com.decagon.kindredhairapigrp1.services.Impl;


import com.decagon.kindredhairapigrp1.DTO.ProductDTO;
import com.decagon.kindredhairapigrp1.models.*;
import com.decagon.kindredhairapigrp1.repository.ProductRecommendationRepository;
import com.decagon.kindredhairapigrp1.DTO.AnswerResponseDTO;
import com.decagon.kindredhairapigrp1.DTO.UserDTO;
import com.decagon.kindredhairapigrp1.repository.UserDetailsRepository;
import com.decagon.kindredhairapigrp1.repository.UserRepository;
import com.decagon.kindredhairapigrp1.services.ProductService;
import com.decagon.kindredhairapigrp1.services.UserService;
import com.decagon.kindredhairapigrp1.utils.TestUtils;
import com.decagon.kindredhairapigrp1.utils.api.response.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;


    @MockBean
    ProductService productService;

    @MockBean
    ProductRecommendationRepository mockedProductRecommendationRepository;


    private User user;
    private UserDTO userDto;
    private AnswerResponseDTO answerDTO;
    private UserDetails userDetails;
    private Product product;
    ProductRecommendation productRecommendation;
    List<ProductDTO> productDTOList;

    @BeforeEach
    public void setUp(){
        try {
            productDTOList = (List<ProductDTO>) TestUtils.deserialize("src/test/java/com/decagon/kindredhairapigrp1/utils/test_product_list.data");
        } catch (Exception e) {
            e.printStackTrace();
            productDTOList =   new ArrayList<>();
        }

        userDto = new UserDTO();
        userDto.setEmail("hello@hello.com");
        userDto.setPassword(bCryptPasswordEncoder.encode("foo"));
        userDto.setRole("USER");
        userDto.setStatus("");
        userDto.setPhoneNumber("0909999999");

        answerDTO = new AnswerResponseDTO();
        answerDTO.setDescribe(List.of("weak,damaged"));
        answerDTO.setAllergies(List.of("poverty"));
        answerDTO.setPriority(List.of("quality"));
        answerDTO.setWhatElse(List.of("I'm transitioning to natural hair"));
        answerDTO.setPorosity("1 - 3 hours");
        answerDTO.setGoals(List.of("strength"));
        answerDTO.setStyles(List.of("blow out"));
        answerDTO.setBrandsIUse(List.of("Pantene"));
        answerDTO.setBrandsIDontLike(List.of("none"));
        answerDTO.setRating("4");
        answerDTO.setBudget("$12 - $15");
        answerDTO.setType(List.of("4b - tight coils"));

        user = new User();
        user.setId(1L);
        user.setEmail("hello@hello.com");
        user.setRole("USER");
        user.setStatus("");
        user.setPhoneNumber("0909999999");
        user.setEnabled(true);
        user.setPassword(bCryptPasswordEncoder.encode("foo"));

        userDetails = new UserDetails();
        userDetails.setUser(user);
        userDetails.setId(1L);

        product = new Product();
        product.setId(1L);

        productRecommendation = new ProductRecommendation("recommendation 1","describe", userDetails, product );
    }

    @Test
    @DisplayName("Should return user already exist response")
    public void whenRegisteringWithExistingEmail(){
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(user));
        assertEquals(userService.registerUser(userDto, answerDTO).getMessage(), "The user email exists already");
        assertEquals(userService.registerUser(userDto, answerDTO).getStatus(), 400);
    }


    @Test
    @DisplayName("Should return appropriate response status based on the user id provided")
    void testGetUserProfileDetails() {
        //arrange
        User user = new User();
        Product product = new Product();
        CartItem cartItem = new CartItem();
        UserDetails userDetails = new UserDetails();

        cartItem.setQuantity(1);
        cartItem.setProduct(product);

        userDetails.setFirstname("Kindred");
        userDetails.setLastname("Hair");
        userDetails.setCartItems(Set.of(cartItem));

        user.setId(1L);
        user.setUserDetails(userDetails);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        //id < 0
        ResponseEntity responseEntity = userService.getUserProfileDetails(0L);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        //user with id does not exist
        responseEntity = userService.getUserProfileDetails(100000000L);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        //user exists but not enabled
        responseEntity = userService.getUserProfileDetails(1L);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());

        //user enabled
        user.setEnabled(true);
        responseEntity = userService.getUserProfileDetails(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @Test
    void shouldUpdateRecommendationAndReturnAnOkStatus_WhenCorrectIdIsPassed(){
        //Arrange
        List<ProductRecommendation> productRecommendations = new ArrayList<>(TestUtils.generateProductRecommendations());
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(mockedProductRecommendationRepository.saveAll(anyCollection())).thenReturn(productRecommendations);
        doNothing().when(mockedProductRecommendationRepository).deleteInBatch(anyCollection());
        when(productService.findAllAvailableProducts()).thenReturn(productDTOList);

        //Act
        ApiResponse<Object> response = userService.updateUserRecommendation(1L, TestUtils.getAnswerResponseDTO());

        //Assert
        assertEquals(200, response.getStatus());
        assertEquals("User profile updated", response.getMessage());
        verify(userRepository, times(1)).findById(anyLong());
        verify(mockedProductRecommendationRepository, times(1)).saveAll(anyCollection());
        verify(mockedProductRecommendationRepository, times(1)).deleteInBatch(anyCollection());
        verify(productService, times(3)).findAllAvailableProducts();
    }

    @Test
    void shouldThrowError_WhenInvalidIdIsPassedToUpdateUserProfile(){
        //Arrange
        List<ProductRecommendation> productRecommendations = new ArrayList<>(TestUtils.generateProductRecommendations());
        when(userRepository.findById(anyLong())).thenReturn(null);

        //Act
        ApiResponse<Object> response = userService.updateUserRecommendation(1L, TestUtils.getAnswerResponseDTO());

        //Assert
        assertEquals(500, response.getStatus());
        assertEquals("Failed to update user profile", response.getMessage());
        verify(userRepository, times(1)).findById(anyLong());
    }
}

