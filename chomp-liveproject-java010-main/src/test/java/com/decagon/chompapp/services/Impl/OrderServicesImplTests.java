package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.dtos.OrderDto;
import com.decagon.chompapp.dtos.OrderDtoForStatusUpdate;
import com.decagon.chompapp.dtos.ShippingAddressDto;
import com.decagon.chompapp.enums.OrderStatus;
import com.decagon.chompapp.enums.PaymentMethod;
import com.decagon.chompapp.enums.ShippingMethod;
import com.decagon.chompapp.models.Order;
import com.decagon.chompapp.models.OrderItem;
import com.decagon.chompapp.models.User;
import com.decagon.chompapp.repositories.OrderRepository;
import com.decagon.chompapp.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class OrderServicesImplTests {

    @Mock
    UserRepository userRepository;

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderServicesImpl orderServicesImpl;

    User user1;

    UserDetails userDetails;

    Pageable pageable;

    Page<Order> orders;

    Order newOrder;

    List<OrderItem> orderItems= new ArrayList<>();

    OrderDtoForStatusUpdate orderDtoForStatusUpdate = OrderDtoForStatusUpdate.builder()
            .status(OrderStatus.PENDING)
            .build();

    @BeforeEach
    void setUp() {
        user1 = User.builder()
                .userId(1L)
                .email("james@mail.com")
                .password("password")
                .username("james@mail.com")
                .firstName("James")
                .build();


        newOrder = Order.builder().orderId(1L).flatRate(0.00).totalPrice(1000.00).orderItems(orderItems).user(user1).build();
        List<Order> listOfOrders = new ArrayList<>();
        listOfOrders.add(newOrder);
        pageable = PageRequest.of(0, 10, Sort.by("orderId"));
        orders = new PageImpl<>(listOfOrders);
    }

    @Test
    void viewOrderHistoryForAPremiumUser() {
        userDetails = new org.springframework.security.core.userdetails.User(user1.getEmail(), user1.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_PREMIUM")));
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        Mockito.when(userRepository.findByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.ofNullable(user1));

        Mockito.when(orderRepository.findAllByUser(pageable, user1)).thenReturn(orders);
        String sortBy = "orderId";
        String sortDir = "asc";
        orderServicesImpl.viewOrderHistoryForAPremiumUser(0,10,sortBy,sortDir);
        verify(orderRepository).findAllByUser(pageable, user1);
    }

    @Test
    void updateOrderStatusAdmin() {
        Mockito.when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(newOrder));
        Mockito.when(orderRepository.save(any())).thenReturn(newOrder);
        orderServicesImpl.updateOrderStatus(1L,orderDtoForStatusUpdate);
        verify(orderRepository).findById(anyLong());
        verify(orderRepository).save(any());
        Assertions.assertEquals("DELIVERED",OrderStatus.DELIVERED.toString());
        orderDtoForStatusUpdate.setStatus(OrderStatus.PENDING);
        orderServicesImpl.updateOrderStatus(1L,orderDtoForStatusUpdate);
        Assertions.assertEquals("PENDING",OrderStatus.PENDING.toString());
        orderDtoForStatusUpdate.setStatus(OrderStatus.CANCEL);
        orderServicesImpl.updateOrderStatus(1L,orderDtoForStatusUpdate);
        Assertions.assertEquals("CANCEL",OrderStatus.CANCEL.toString());
    }
}