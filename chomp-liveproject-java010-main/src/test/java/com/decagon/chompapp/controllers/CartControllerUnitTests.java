package com.decagon.chompapp.controllers;

import com.decagon.chompapp.dtos.CartDto;
import com.decagon.chompapp.dtos.CartItemDto;
import com.decagon.chompapp.models.*;
import com.decagon.chompapp.services.CartService;
import com.decagon.chompapp.services.Impl.CartServiceImpl;
import com.decagon.chompapp.services.paystack.PaymentServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class CartControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CartServiceImpl cartService;

    @MockBean
    private PaymentServiceImpl paymentService;

    @Autowired
    ObjectMapper objectMapper;


    Product product1, product2;
    User user;
    Cart userCart;
    CartDto userCartDto;
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
        userCart = Cart.builder().cartId(1L).user(user).cartItemList(new ArrayList<>()).cartTotal(0).quantity(0).build();
        user = User.builder().userId(1L).email("ukeloveth247@gmail.com").firstName("Loveth").cart(userCart).build();
        userCartDto = CartDto.builder().cartId(1L).userId(user.getUserId()).cartItemList(new ArrayList<>()).cartTotal(0).quantity(0).build();
        cartItem1 = CartItem.builder().cartItemId(1L).cart(userCart).product(product1).quantity(0).subTotal(0).build();
        cartItem2 = CartItem.builder().cartItemId(2L).cart(userCart).product(product2).quantity(0).subTotal(0).build();
        cartItemDto1 = CartItemDto.builder().cartId(cartItem1.getCartItemId())
                .productId(product1.getProductId()).productImage(product1.getProductImage())
                .productName(product1.getProductName()).build();
        cartItemDto2 = CartItemDto.builder().cartId(cartItem2.getCartItemId())
                .productId(product2.getProductId()).productImage(product2.getProductImage())
                .productName(product2.getProductName()).build();
    }

    @Test
    void addToCart() throws Exception {
        ResponseEntity<CartItemDto> responseEntity = new ResponseEntity<>(cartItemDto1, HttpStatus.OK);
        Mockito.when(cartService.addToCart(anyLong())).thenReturn(responseEntity);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/cart/add-to-cart/{productId}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String expectedResponse = objectMapper.writeValueAsString(responseEntity.getBody());
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void clearCart() throws Exception {
        ResponseEntity<String> responseEntity = new ResponseEntity<>("cart has been cleared",HttpStatus.OK);
        Mockito.when(cartService.clearCart()).thenReturn(responseEntity);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/cart/clear-cart").accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(responseEntity)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String expectedResponse = objectMapper.writeValueAsString(responseEntity.getBody()).replace("\"","");
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedResponse,actualResponse);
    }

    @Test
    void reduceItemQuantity() throws Exception {
        ResponseEntity<String> responseEntity = new ResponseEntity<>("cart quantity has been reduced",HttpStatus.OK);
        Mockito.when(cartService.reduceQuantityInCart(anyLong())).thenReturn(responseEntity);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/cart/reduce-item-quantity/{cart-item-id}",2L))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String expectedResponse = objectMapper.writeValueAsString(responseEntity.getBody()).replace("\"","");
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedResponse,actualResponse);

    }

    @Test
    void increaseItemQuantity() throws Exception {
        ResponseEntity<String> responseEntity = new ResponseEntity<>("cart quantity has been increased",HttpStatus.OK);
        Mockito.when(cartService.increaseQuantityInCart(anyLong())).thenReturn(responseEntity);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/cart/increase-item-quantity/{cart-item-id}",1L))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String expectedResponse = objectMapper.writeValueAsString(responseEntity.getBody()).replace("\"","");
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedResponse,actualResponse);

    }

    @Test
    void removeCartItem() throws Exception {
        ResponseEntity<String> responseEntity = new ResponseEntity<>("cart item has been removed",HttpStatus.OK);
        Mockito.when(cartService.removeCartItem(anyLong())).thenReturn(responseEntity);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/cart/remove-cart-item/{cart-item-id}",1L))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String expectedResponse = objectMapper.writeValueAsString(responseEntity.getBody()).replace("\"","");
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedResponse,actualResponse);
    }
    @Test
    void shouldCallAGetMappingMethodAndReturnAResponseEntityOfCartDto() throws Exception {
        ResponseEntity<CartDto> responseEntity = ResponseEntity.ok(userCartDto);
        Mockito.when(cartService.viewCart()).thenReturn(responseEntity);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/cart/view-cart",1L))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }


}