package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.dtos.OrderDto;
import com.decagon.chompapp.dtos.OrderDtoForStatusUpdate;
import com.decagon.chompapp.dtos.OrderResponse;
import com.decagon.chompapp.dtos.OrderResponseDto;
import com.decagon.chompapp.enums.OrderStatus;
import com.decagon.chompapp.exceptions.UserNotFoundException;
import com.decagon.chompapp.models.Order;
import com.decagon.chompapp.models.Product;
import com.decagon.chompapp.models.User;
import com.decagon.chompapp.repositories.AddressRepository;
import com.decagon.chompapp.repositories.OrderRepository;
import com.decagon.chompapp.repositories.UserRepository;
import com.decagon.chompapp.services.OrderServices;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServicesImpl implements OrderServices {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    public ResponseEntity<OrderResponse> viewOrderHistoryForAPremiumUser(int pageNo, int pageSize, String sortBy, String sortDir) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        User currentUser = userRepository.findByUsernameOrEmail(username, username).orElseThrow( () ->
         new UserNotFoundException("User does not exist in the database"));
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())  ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            Pageable pageable = PageRequest.of(pageNo, pageSize,sort);
            Page<Order> orders = orderRepository.findAllByUser(pageable, currentUser);
            return getOrderResponseEntity(orders);
    }

    @Override
    public ResponseEntity<OrderResponseDto> updateOrderStatus (Long orderId, OrderDtoForStatusUpdate orderDtoForStatusUpdate) {

            Optional<Order> orderToUpdate = orderRepository.findById(orderId);
            if (OrderStatus.DELIVERED.equals(orderDtoForStatusUpdate.getStatus())) orderToUpdate.orElseThrow().setStatus(OrderStatus.DELIVERED);
            if (OrderStatus.CANCEL.equals(orderDtoForStatusUpdate.getStatus())) orderToUpdate.orElseThrow().setStatus(OrderStatus.CANCEL);
            if (OrderStatus.PENDING.equals(orderDtoForStatusUpdate.getStatus())) orderToUpdate.orElseThrow().setStatus(OrderStatus.PENDING);
            orderRepository.save(orderToUpdate.orElseThrow());
            return ResponseEntity.ok(convertOrderEntityToOrderResponseDto(orderToUpdate.orElseThrow()));

    }

    private ResponseEntity<OrderResponse> getOrderResponseEntity(Page<Order> orders) {
        List<Order> listOfOrders = orders.getContent();
        var content =  listOfOrders.stream().map(this::convertOrderEntityToOrderResponseDto).collect(Collectors.toList());
        OrderResponse orderResponse= new OrderResponse(
                content,
                orders.getNumber(),
                orders.getSize(),
                orders.getTotalElements(),
                orders.getTotalPages(),
                orders.isLast()
        );
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(orderResponse, httpHeaders, HttpStatus.OK);
    }

    private OrderResponseDto convertOrderEntityToOrderResponseDto(Order order) {
        return getOrderResponseDto(order);
    }

    static OrderResponseDto getOrderResponseDto(Order order) {
        return OrderResponseDto.builder()
                .orderId(order.getOrderId())
                .subTotal(order.getTotalPrice())
                .total(order.getFlatRate() + order.getTotalPrice())
                .dateOrdered(order.getDateOrdered())
                .flatRate(order.getFlatRate())
                .shippingAddress(order.getShippingAddress())
                .status(order.getStatus())
                .paymentMethod(order.getPaymentMethod())
                .shippingMethod(order.getShippingMethod())
                .productList(order.getOrderItems().stream().map(orderItem -> Product.builder()
                        .productId(orderItem.getProduct().getProductId())
                        .size(orderItem.getProduct().getSize())
                        .productName(orderItem.getProduct().getProductName())
                        .productPrice(orderItem.getProduct().getProductPrice())
                        .productImage(orderItem.getProduct().getProductImage())
                        .createdDate(orderItem.getProduct().getCreatedDate())
                        .build()).collect(Collectors.toList()))
                .build();
    }
}
