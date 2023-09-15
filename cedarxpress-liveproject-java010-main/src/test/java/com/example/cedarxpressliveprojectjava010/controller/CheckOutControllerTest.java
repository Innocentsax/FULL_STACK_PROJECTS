package com.example.cedarxpressliveprojectjava010.controller;

import com.example.cedarxpressliveprojectjava010.dto.CheckOutDto;
import com.example.cedarxpressliveprojectjava010.dto.OrderDto;
import com.example.cedarxpressliveprojectjava010.entity.*;
import com.example.cedarxpressliveprojectjava010.enums.DeliveryStatus;
import com.example.cedarxpressliveprojectjava010.enums.Payment;
import com.example.cedarxpressliveprojectjava010.service.CheckOutService;
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
import java.util.List;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {CheckOutController.class})
@ExtendWith(SpringExtension.class)
class CheckOutControllerTest {
    @Autowired private CheckOutController checkOutController;

    @MockBean CheckOutService checkOutService;
    @MockBean private Authentication auth;
    @MockBean SecurityContext securityContext;



    Cart cart;
    User user;
    Address address;
    Order order;
    CartItem cartItem;
    OrderItem orderItem;
    CheckOutDto checkOutDto;
    Product product;
    OrderDto orderDto;
    List<CartItem> cartItemList = new ArrayList<>();
    List<OrderItem> orderItemList = new ArrayList<>();
    @BeforeEach
    void setUp() {
        auth = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);


        user = User.builder().firstName("Darlington").email("email").build();
        address = Address.builder().address("Edo").user(user).zipCode(898).build();
        address.setId(1L);
        cart = Cart.builder().customer(user).build();
        product = Product.builder().productName("Office Chair").price(200.00).build();
        cartItem = CartItem.builder().cart(cart).product(product).unit(0).build();
        cartItemList.add(cartItem);
        cartItemList.add(cartItem);
        cart.setCartItems(cartItemList);


        checkOutDto = CheckOutDto.builder()
                .address_id(1L)
                .payment("CARD")
                .build();

        orderItem = OrderItem.builder()
                .product(product)
                .build();
        orderItemList.add(orderItem);
        orderItemList.add(orderItem);
        order = Order.builder()
                .paymentMethod(Payment.CARD)
                .price(400.00)
                .timeOfCompletion(null)
                .deliveryStatus(DeliveryStatus.PENDING)
                .address(address).build();

        orderDto = OrderDto.builder()
                .deliveryStatus(DeliveryStatus.PENDING)
                .address(address.getAddress())
                .paymentMethod(Payment.CARD)
                .price(400.00)
                .user(user)
                .customerOrder(orderItemList)
                .timeOfCompletion(null)
                .build();
    }

    @Test
    void checkOut() throws Exception {

        given(checkOutService.makeOrder(checkOutDto)).willReturn(ResponseEntity.ok(orderDto));
        String content = (new ObjectMapper()).writeValueAsString(orderDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customer/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        ResultActions actual = MockMvcBuilders.standaloneSetup(this.checkOutController)
                .build()
                .perform(requestBuilder);

        actual.andExpect(MockMvcResultMatchers.status().is(200));
    }
}