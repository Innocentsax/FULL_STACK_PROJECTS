package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.exceptions.ResourceNotFoundException;
import com.decagon.chompapp.models.Category;
import com.decagon.chompapp.models.Favorites;
import com.decagon.chompapp.models.Product;
import com.decagon.chompapp.models.User;
import com.decagon.chompapp.repositories.FavoriteRepository;
import com.decagon.chompapp.repositories.ProductRepository;
import com.decagon.chompapp.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@Slf4j
@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class FavoritesServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    FavoriteRepository favoriteRepository;

    @InjectMocks
    FavoritesServiceImpl favoritesServiceImpl;

    User user1 = User.builder()
            .userId(1L)
            .email("james@mail.com")
            .password("password")
            .username("james")
            .firstName("James")
            .build();

    User user2 = User.builder().userId(2L).build();

    Category burger = Category.builder()
            .categoryId(1L)
            .categoryName("Burger")
            .build();

    Product favoriteProduct = Product.builder()
            .productId(1L)
            .productName("Burger Spice")
            .category(burger)
            .build();

    Favorites favorite = Favorites.builder()
            .favoriteId(1L)
            .product(favoriteProduct)
            .user(user1)
            .build();
    UserDetails userDetails = new org.springframework.security.core.userdetails.User(user1.getEmail(), user1.getPassword(), List.of(new SimpleGrantedAuthority("PREMIUM")));

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        favorite.setProduct(favoriteProduct);
        favorite.setUser(user1);
    }

    @Test
    public void testAddProductToFavorite() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);

        Mockito.when(userRepository.findByEmail(any())).thenReturn(Optional.of(user1));

        Mockito.when(productRepository.findById(anyLong())).thenReturn(Optional.of(favoriteProduct));

        Mockito.when(favoriteRepository.existsByUserAndProduct(any(), any())).thenReturn(true);

        ResponseEntity<String> responseEntity = favoritesServiceImpl.addProductToFavorite(1L);
        Assertions.assertEquals(responseEntity.getBody(), favoriteProduct.getProductName() + " is already your favorite");
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST );
    }

    @Test
    public void testAddProductToFavoriteThatIsAlreadyInFavorite() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);

        Mockito.when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user1));

        Mockito.when(productRepository.findById(any())).thenReturn(Optional.of(favoriteProduct));

        Mockito.when(favoriteRepository.existsByUserAndProduct(any(), any())).thenReturn(false);
        Mockito.when(favoriteRepository.save(any())).thenReturn(favorite);

        ResponseEntity<String> responseEntity = favoritesServiceImpl.addProductToFavorite(favoriteProduct.getProductId());

        Assertions.assertEquals(responseEntity.getBody(), favorite.getProduct().getProductName() + " added to favorite");
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testForExceptionThrownWhenUserRemovesProductThatIsNotInFavorite() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);

        Mockito.when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user1));
        Mockito.when(favoriteRepository.findFavoritesByProduct_ProductIdAndUser_UserId(anyLong(),anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, ()-> {
            favoritesServiceImpl.removeProductFromFavorite(1L);
        });
    }

    @Test
    void TestForRightResponseWhenUserRemoveProductFromFavorite() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);

        Mockito.when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user1));
        Mockito.when(favoriteRepository.findFavoritesByProduct_ProductIdAndUser_UserId(anyLong(),anyLong())).thenReturn(Optional.of(favorite));

        ResponseEntity<String> responseEntity1 = favoritesServiceImpl.removeProductFromFavorite(1L);

        Assertions.assertEquals(responseEntity1.getBody(), favorite.getProduct().getProductName() + " has been removed.");
    }
}