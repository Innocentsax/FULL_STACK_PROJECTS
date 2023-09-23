package com.example.food.controllers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

import com.example.food.pojos.CartResponse;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CartController.class})
@ExtendWith(SpringExtension.class)
class CartControllerTest {
    @Autowired
    private CartController cartController;

    @MockBean
    private CartService cartService;

    @Test
    void testRemoveCartItem() throws Exception {
        // Arrange
        when(cartService.removeCartItem(anyLong())).thenReturn(new BaseResponse());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/cart/remove-cart-item/{cart-item-id}", 42L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(cartController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"code\":-1,\"description\":\"An error occurred. Error message : ${errorMessage}\"}"));
    }

    @Test
    void testAddToCart() throws Exception {
        // Arrange
        when(cartService.addCartItem((Long) any())).thenReturn(new CartResponse());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/cart/add-to-cart/{productId}", 123L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(cartController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"code\":-1,\"description\":\"An error occurred. Error message : ${errorMessage}\",\"cartItemList\":null,"
                                        + "\"quantity\":0,\"cartTotal\":null}"));
    }

    @Test
    void testClearCart() throws Exception {
        // Arrange
        when(cartService.clearCart()).thenReturn(new CartResponse());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/cart/clear-cart");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(cartController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"code\":-1,\"description\":\"An error occurred. Error message : ${errorMessage}\",\"cartItemList\":null,"
                                        + "\"quantity\":0,\"cartTotal\":null}"));
    }

    @Test
    void testViewCartItems() throws Exception {
        // Arrange
        when(cartService.viewCartItems()).thenReturn(new CartResponse());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cart/view-cart");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(cartController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"code\":-1,\"description\":\"An error occurred. Error message : ${errorMessage}\",\"cartItemList\":null,"
                                        + "\"quantity\":0,\"cartTotal\":null}"));
    }

    @Test
    void testReduceProduct() throws Exception {
        // Arrange
        when(cartService.reduceProductQuantity((Long) any())).thenReturn(new CartResponse());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/cart/reduce-product/{productId}",
                123L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(cartController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"code\":-1,\"description\":\"An error occurred. Error message : ${errorMessage}\",\"cartItemList\":null,"
                                        + "\"quantity\":0,\"cartTotal\":null}"));
    }
}

