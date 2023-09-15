package com.example.cedarxpressliveprojectjava010.service;

import com.example.cedarxpressliveprojectjava010.dto.OrderDto;
import com.example.cedarxpressliveprojectjava010.entity.Address;
import com.example.cedarxpressliveprojectjava010.entity.Order;
import com.example.cedarxpressliveprojectjava010.entity.User;
import com.example.cedarxpressliveprojectjava010.enums.DeliveryStatus;
import com.example.cedarxpressliveprojectjava010.enums.Payment;
import com.example.cedarxpressliveprojectjava010.repository.OrderRepository;
import com.example.cedarxpressliveprojectjava010.repository.UserRepository;
import com.example.cedarxpressliveprojectjava010.service.implementation.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    ModelMapper modelMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    OrderRepository orderRepository;

    private Order order1;
    private Order order2;
    private User customer;

    @BeforeEach
    void setUp() {

        customer = User.builder()
                .email("customer@gmail.com")
                .lastName("customer firstname")
                .firstName("customer lastname")
                .build();
        customer.setId(1L);


        Address orderAddress = Address.builder()
                .address("test order address")
                .build();

         order1 = Order.builder()
                .address(orderAddress)
                .customer(customer)
                .price(7000.00)
                .deliveryStatus(DeliveryStatus.DELIVERED)
                .paymentMethod(Payment.CARD)
                .build();
        order1.setId(1L);

        order2 = Order.builder()
                .address(orderAddress)
                .customer(customer)
                .price(9000.00)
                .deliveryStatus(DeliveryStatus.DELIVERED)
                .paymentMethod(Payment.CARD)
                .build();
        order2.setId(2L);


        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(customer));




        OrderDto orderDto1 = OrderDto.builder()
                .price(7000.00)
                .deliveryStatus(DeliveryStatus.DELIVERED)
                .paymentMethod(Payment.CARD)
                .build();



        Mockito.when(modelMapper.map(order1, OrderDto.class)).thenReturn(orderDto1);

    }

    @Test
    void should_viewParticularUserOrder() {
        Mockito.when(orderRepository.findOrderByCustomerAndId(customer, 1L))
                .thenReturn(Optional.of(order1));

        ResponseEntity<OrderDto> userSingleOrder = orderService.viewParticularUserOrder(1L, 1L);
        assertThat(Objects.requireNonNull(userSingleOrder.getBody()).getPrice())
                .isEqualTo(7000.00);
    }

    @Test
    void should_viewUserOrders() {
        OrderDto orderDto2 = OrderDto.builder()
                .price(9000.00)
                .deliveryStatus(DeliveryStatus.DELIVERED)
                .paymentMethod(Payment.CARD)
                .build();

        Mockito.when(orderRepository.findAllByCustomer(customer))
                .thenReturn(List.of(order1, order2));

        Mockito.when(modelMapper.map(order2, OrderDto.class)).thenReturn(orderDto2);

        List<OrderDto> orderDtos = orderService.viewUserOrders(1L);
        assertThat(orderDtos.size()).isGreaterThan(0);
    }

}