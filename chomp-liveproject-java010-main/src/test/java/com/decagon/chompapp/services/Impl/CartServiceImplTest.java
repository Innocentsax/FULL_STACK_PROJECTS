package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.dtos.CartDto;
import com.decagon.chompapp.dtos.CartItemDto;
import com.decagon.chompapp.models.*;
import com.decagon.chompapp.repositories.CartItemRepository;
import com.decagon.chompapp.repositories.CartRepository;
import com.decagon.chompapp.repositories.ProductRepository;
import com.decagon.chompapp.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {
    @InjectMocks
    CartServiceImpl cartService;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductRepository productRepository;

    Product product1, product2;
    User user;
    Cart userCart;
    CartItem cartItem1, cartItem2;
    Category category1, category2;
    CartItemDto cartItemDto1;
    CartItemDto cartItemDto2;


    @BeforeEach
    void setUp() {

        category1 = Category.builder().categoryId(1L).build();
        category2 = Category.builder().categoryId(2L).build();
        product1 = Product.builder().productId(1L)
                .productImage("jsdhjhgsdj")
                .productName("Burger")
                .productPrice(2000).category(category1).build();
        product2 = Product.builder().productId(1L)
                .productImage("jsdhjhgsdj")
                .productName("Fries")
                .productPrice(1500).category(category2).build();
        user = User.builder().userId(1L).email("ukeloveth247@gmail.com").firstName("Loveth").build();
        userCart = Cart.builder().cartId(1L).user(user).cartItemList(new ArrayList<>()).cartTotal(0).quantity(0).build();
        user.setCart(userCart);
        cartItem1 = CartItem.builder().cartItemId(1L).cart(userCart).product(product1).quantity(0).subTotal(0).build();
        cartItem2 = CartItem.builder().cartItemId(2L).cart(userCart).product(product2).quantity(0).subTotal(0).build();
        cartItemDto1 = CartItemDto.builder().cartId(cartItem1.getCartItemId())
                .productId(product1.getProductId()).productImage(product1.getProductImage())
                .productName(product1.getProductName()).build();
        cartItemDto2 = CartItemDto.builder().cartId(cartItem2.getCartItemId())
                .productId(product2.getProductId()).productImage(product2.getProductImage())
                .productName(product2.getProductName()).build();

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

    }

    @Test
    void addToCart() throws Exception {
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(user.getUsername());
        Mockito.when(userRepository.findByUsernameOrEmail(any(), any())).thenReturn(Optional.of(user));
        Mockito.when(productRepository.findById(any())).thenReturn(Optional.of(product1));
        Mockito.when(cartItemRepository.findByCartAndProduct(any(), any())).thenReturn(Optional.empty());
        Mockito.when(cartRepository.save(any())).thenReturn(userCart);
        Mockito.when(cartItemRepository.save(any())).thenReturn(cartItem1);
        ResponseEntity<CartItemDto> responseEntity = cartService.addToCart(1);
        Assertions.assertThat(Objects.requireNonNull(responseEntity.getBody()).getProductName()).isEqualTo(cartItemDto1.getProductName());
        Mockito.when(cartItemRepository.findByCartAndProduct(any(), any())).thenReturn(Optional.of(cartItem1));
        ResponseEntity<CartItemDto> responseEntity2 = cartService.addToCart(1);
        Assertions.assertThat(Objects.requireNonNull(responseEntity2.getBody()).getProductName()).isEqualTo(cartItemDto1.getProductName());
    }

    @Test
    void clearCart() {
        Mockito.doNothing().when(cartItemRepository).deleteAllByCart_CartId(anyLong());
        Mockito.when(userRepository.findByUsernameOrEmail(any(), any())).thenReturn(Optional.of(user));
        ResponseEntity<String> responseEntity = cartService.clearCart();
        Assertions.assertThat(responseEntity.getBody()).isEqualTo("Cart cleared successfully");

    }

    @Test
    void removeCartItem() {
        Mockito.when(cartItemRepository.findByCart_CartIdAndCartItemId(anyLong(), anyLong())).thenReturn(Optional.of(cartItem1));
        Mockito.when(userRepository.findByUsernameOrEmail(any(), any())).thenReturn(Optional.of(user));
        Mockito.doNothing().when(cartItemRepository).deleteById(anyLong());
        Mockito.when(cartRepository.save(any())).thenReturn(userCart);
        ResponseEntity<String> responseEntity = cartService.removeCartItem(1);
        Assertions.assertThat(responseEntity.getBody()).isEqualTo("Item removed from user cart");
    }

    @Test
    void reduceQuantityInCart() {
        cartItem1.setQuantity(10);
        Mockito.when(userRepository.findByUsernameOrEmail(any(), any())).thenReturn(Optional.of(user));
        Mockito.when(cartItemRepository.findByCart_CartIdAndCartItemId(anyLong(),anyLong())).thenReturn(Optional.of(cartItem1));
        Mockito.doNothing().when(cartItemRepository).deleteById(anyLong());
        Mockito.when(cartRepository.save(any())).thenReturn(userCart);
        ResponseEntity<String>responseEntity = cartService.reduceQuantityInCart(1);
        Assertions.assertThat(responseEntity.getBody()).isEqualTo("Quantity reduced by 1");
        Assertions.assertThat(cartItem1.getQuantity()).isEqualTo(9);
        cartItem1.setQuantity(1);
        ResponseEntity<String>responseEntity2 = cartService.reduceQuantityInCart(1);
        Assertions.assertThat(responseEntity2.getBody()).isEqualTo("Item removed from user cart");
    }
    @Test
    void increaseQuantityInCart() {
        Mockito.when(userRepository.findByUsernameOrEmail(any(), any())).thenReturn(Optional.of(user));
        Mockito.when(cartItemRepository.findByCart_CartIdAndCartItemId(anyLong(),anyLong())).thenReturn(Optional.of(cartItem1));
        Mockito.when(cartItemRepository.save(any())).thenReturn(cartItem1);
        ResponseEntity<String> responseEntity = cartService.increaseQuantityInCart(1);
        Assertions.assertThat(responseEntity.getBody()).isEqualTo("Quantity increased by 1");

    }
    @Test
    void shouldReturnACartDtoWhenViewCartMethodIsCalledOrThrowAnExceptionIfUserIsNotAuthorized(){
        Mockito.when(userRepository.findByUsernameOrEmail(any(), any())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, ()-> cartService.viewCart());

        Mockito.when(userRepository.findByUsernameOrEmail(any(), any())).thenReturn(Optional.of(user));
        Mockito.when(cartRepository.findByUser(any())).thenReturn(userCart);
        var responseEntity = cartService.viewCart();
        Assertions.assertThat(responseEntity.getBody().getCartId()).isEqualTo(userCart.getCartId());
        Assertions.assertThat(responseEntity.getBody()).isInstanceOf(CartDto.class);
    }
}