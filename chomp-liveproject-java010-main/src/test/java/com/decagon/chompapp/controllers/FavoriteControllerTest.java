package com.decagon.chompapp.controllers;

import com.decagon.chompapp.models.User;
import com.decagon.chompapp.services.FavoritesService;
import com.decagon.chompapp.services.paystack.PaymentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = SpringSecurityForUserControllerImplTestConfig.class)
public class FavoriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FavoritesService favoritesService;

    @MockBean
    private PaymentServiceImpl paymentService;


    User user1 = User.builder()
            .userId(1L)
            .email("james@mail.com")
            .password("password")
            .username("james")
            .firstName("James")
            .build();

    ResponseEntity<String> responseEntity;

    UserDetails userDetails = new org.springframework.security.core.userdetails.User(user1.getEmail(),
            user1.getPassword(), List.of(new SimpleGrantedAuthority("PREMIUM")));


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        responseEntity = new ResponseEntity<>("added successfully", HttpStatus.OK);

    }


    @Test
    @WithUserDetails("james")
    public void whenAddProductToFavoriteThenReturn200() throws Exception {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        Mockito.when(favoritesService.addProductToFavorite(anyLong())).thenReturn(responseEntity);
        mockMvc.perform(MockMvcRequestBuilders.post("/favorite/{productId}/add",1L))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    @Test
    public void whenRemoveProductToFavoriteThenReturn200() throws Exception {
        mockMvc.perform(delete("/favorite/1/remove")
                .pathInfo(anyString()))
                .andExpect(status().isOk());
    }

}
