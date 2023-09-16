package com.decagon.chompapp.controllers;

import com.decagon.chompapp.dtos.OrderDto;
import com.decagon.chompapp.dtos.OrderResponseDto;
import com.decagon.chompapp.dtos.ShippingAddressDto;
import com.decagon.chompapp.enums.OrderStatus;
import com.decagon.chompapp.enums.PaymentMethod;
import com.decagon.chompapp.enums.ShippingMethod;
import com.decagon.chompapp.models.Cart;
import com.decagon.chompapp.models.User;
import com.decagon.chompapp.services.CheckoutService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ContextConfiguration(classes = {CheckoutController.class})
@ExtendWith(SpringExtension.class)
class CheckoutControllerUnitTest {

    @Autowired private CheckoutController checkoutController;
    @MockBean
    private CheckoutService checkoutService;
    @MockBean private Authentication authentication;
    @MockBean
    SecurityContext context;
    private OrderDto orderDto;
    private ShippingAddressDto shippingAddressDto;
    private OrderResponseDto orderResponseDto;


    @BeforeEach
    void setUp() {
        authentication = mock(Authentication.class);
        context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);
        User user = User.builder().firstName("lawal").email("email@gmail.com").build();
        shippingAddressDto = ShippingAddressDto.builder()
                .city("djfhds")
                .email("jdf@gmail.com")
                .isDefaultShippingAddress(true)
                .build();
        orderDto = OrderDto.builder()
                .paymentType(PaymentMethod.PAY_WITH_CARD)
                .shippingMethod(ShippingMethod.FLAT_RATE)
                .shippingAddress(shippingAddressDto)
                .build();
        orderResponseDto = OrderResponseDto.builder()
                .shippingMethod(orderDto.getShippingMethod())
                .paymentMethod(orderDto.getPaymentType())
                .status(OrderStatus.PENDING)
                .dateOrdered(new Date())
                .build();
        Cart userCart = Cart.builder().cartId(1L).user(user).cartItemList(new ArrayList<>()).cartTotal(0).quantity(0).build();
        userCart.setUser(user);
    }

    @Test
    void checkout() throws Exception {

        given(checkoutService.checkout(orderDto)).willReturn(ResponseEntity.ok(orderResponseDto));
        String content = (new ObjectMapper().writeValueAsString(orderResponseDto));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actions = MockMvcBuilders.standaloneSetup(this.checkoutController).build().perform(requestBuilder);
        actions

                .andExpect(MockMvcResultMatchers.status().is(200));
    }
}