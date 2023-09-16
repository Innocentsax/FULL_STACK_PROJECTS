package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.dtos.OrderDto;
import com.decagon.chompapp.dtos.OrderResponseDto;
import com.decagon.chompapp.enums.OrderStatus;
import com.decagon.chompapp.exceptions.CartIsEmptyException;
import com.decagon.chompapp.models.*;
import com.decagon.chompapp.repositories.*;
import com.decagon.chompapp.services.CartService;
import com.decagon.chompapp.services.CheckoutService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.stream.Collectors;


import static com.decagon.chompapp.services.Impl.OrderServicesImpl.getOrderResponseDto;



@Service
@Slf4j
public class CheckoutServiceImpl implements CheckoutService {

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final ShippingAddressRepository shippingAddressRepository;

    private final CartService cartService;

    private final ModelMapper modelMapper;

    @Autowired
    public CheckoutServiceImpl(UserRepository userRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository, ShippingAddressRepository shippingAddressRepository, CartServiceImpl cartService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.shippingAddressRepository = shippingAddressRepository;
        this.cartService = cartService;
        this.modelMapper = modelMapper;
    }



    @Override
    @Transactional
    public ResponseEntity<OrderResponseDto> checkout(OrderDto orderDto) {
        String loggedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userRepository.findByUsernameOrEmail(loggedUserEmail, loggedUserEmail).orElseThrow(
                ()-> new UsernameNotFoundException("User not found"));
        Cart userCart = loggedInUser.getCart();

        if (userCart.getCartItemList().size() < 1) {
            throw new CartIsEmptyException("Your cart is empty");
        }
        Order order = Order.builder()
                .dateOrdered(new Date())
                .paymentMethod(orderDto.getPaymentType())
                .shippingAddress(shippingAddressRepository.save(modelMapper.map(orderDto.getShippingAddress(), ShippingAddress.class)))
                .status(OrderStatus.PENDING)
                .shippingMethod(orderDto.getShippingMethod())
                .user(loggedInUser)
                .orderItems(userCart.getCartItemList().stream().map(this::convertCartItemToOrderItem).collect(Collectors.toList()))
                .flatRate(userCart.getCartItemList().size() * 100.00)
                .totalPrice(userCart.getCartTotal())
                .build();

        orderRepository.save(order);
        cartService.clearCart();
        return ResponseEntity.ok(convertOrderToOrderResponseDto(order));
    }

    private OrderResponseDto convertOrderToOrderResponseDto(Order order) {
        return getOrderResponseDto(order);

    }


    private OrderItem convertCartItemToOrderItem(CartItem cartItem) {
        OrderItem orderItem=OrderItem.builder()

                .quantity(cartItem.getQuantity())
                .product(cartItem.getProduct())
                .subTotal(cartItem.getSubTotal())
                .build();
       return orderItemRepository.save(orderItem);
    }
}
