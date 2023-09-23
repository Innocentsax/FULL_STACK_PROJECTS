package com.example.food.service.serviceImpl;

import com.example.food.model.Order;
import com.example.food.model.Product;
import com.example.food.model.Users;
import com.example.food.pojos.FavouriteProductResponse;
import com.example.food.repositories.FavouritesRepository;
import com.example.food.repositories.OrderRepository;
import com.example.food.repositories.ProductRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.serviceImpl.OrderServiceImpl;
import com.example.food.util.ResponseCodeUtil;
import com.example.food.util.UserUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserUtil userUtil;

    @InjectMocks
    private OrderServiceImpl orderService;


    @Test
    void viewOrderHistory() {
        Users user = Users.builder().email("test@example.com").build();
        Order order = Order.builder().id(1L).build();
        List<Order> orderList = Collections.singletonList(order);

        when(userUtil.getAuthenticatedUserEmail()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(orderRepository.findAllByUserOrderByIdDesc(user)).thenReturn(orderList);

        // Act
        BaseResponse actualResponse = orderService.viewOrderHistory();

        // Assert
        assertNotNull(actualResponse);
        verify(orderRepository, times(1)).findAllByUserOrderByIdDesc(any(Users.class));
    }
}