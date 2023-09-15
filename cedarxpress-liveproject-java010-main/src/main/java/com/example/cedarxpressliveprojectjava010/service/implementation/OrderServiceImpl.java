package com.example.cedarxpressliveprojectjava010.service.implementation;

import com.example.cedarxpressliveprojectjava010.dto.OrderDto;
import com.example.cedarxpressliveprojectjava010.entity.Order;
import com.example.cedarxpressliveprojectjava010.entity.User;
import com.example.cedarxpressliveprojectjava010.enums.DeliveryStatus;
import com.example.cedarxpressliveprojectjava010.exception.OrderNotFoundException;
import com.example.cedarxpressliveprojectjava010.exception.UserNotFoundException;
import com.example.cedarxpressliveprojectjava010.repository.OrderRepository;
import com.example.cedarxpressliveprojectjava010.repository.UserRepository;
import com.example.cedarxpressliveprojectjava010.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository,
                            ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<OrderDto> viewParticularUserOrder(long userId, long orderId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user not found"));

        Order orderByCustomerAndId = orderRepository.findOrderByCustomerAndId(user, orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        OrderDto orderDto = modelMapper.map(orderByCustomerAndId, OrderDto.class);

        return ResponseEntity.ok(orderDto);
    }

    @Override
    public List<OrderDto> viewUserOrders(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user not found"));

        List<Order> Orders = orderRepository.findAllByCustomer(user);
        return Orders.stream()
                .map(order -> modelMapper
                .map(order, OrderDto.class)).collect(Collectors.toList());
    }


    @Override
    public List<OrderDto> viewOrderByStatus(String status) {
        String loggedInUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByEmail(loggedInUserEmail)
                .orElseThrow(() -> new UserNotFoundException("user not found"));
        DeliveryStatus statusMethod = getStatus(status);
        List<Order> ordersByDeliveryStatus = orderRepository.findOrdersByDeliveryStatusAndCustomer(statusMethod,user);
        return ordersByDeliveryStatus.stream().map(order -> modelMapper.map(order,OrderDto.class)).collect(Collectors.toList());
    }

    public DeliveryStatus getStatus(String status){
        status = status.toLowerCase();
        if(status.contains("pending"))return DeliveryStatus.PENDING;
        else if(status.contains("cancel"))return DeliveryStatus.CANCELLED;
        else return DeliveryStatus.DELIVERED;

    }


}
