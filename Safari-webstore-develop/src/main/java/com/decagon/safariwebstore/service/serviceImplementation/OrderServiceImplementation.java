package com.decagon.safariwebstore.service.serviceImplementation;

import com.decagon.safariwebstore.dto.OrderResponseDTO;
import com.decagon.safariwebstore.dto.UserDTO;
import com.decagon.safariwebstore.exceptions.BadRequestException;
import com.decagon.safariwebstore.exceptions.ResourceNotFoundException;
import com.decagon.safariwebstore.model.*;
import com.decagon.safariwebstore.payload.request.UpdateOrderRequest;
import com.decagon.safariwebstore.payload.response.PagedOrderByStatusResponse;
import com.decagon.safariwebstore.repository.OrderRepository;
import com.decagon.safariwebstore.repository.RoleRepository;
import com.decagon.safariwebstore.service.OrderService;
import com.decagon.safariwebstore.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImplementation implements OrderService {

    private final OrderRepository orderRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Override
    public OrderResponseDTO getOrder(Long orderId) {
        return modelMapper.map(orderRepository.findById(orderId).orElseThrow(
                () -> {
                    throw new ResourceNotFoundException("Order not found!");
                }), OrderResponseDTO.class);
    }


    @Override
    public PagedOrderByStatusResponse<OrderResponseDTO> userGetOrderByStatus(String status, User user,
                                                                             int page, int size) {

        checkUserRole(ERole.USER, user);
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

            Page<Order> orderPage = orderRepository.findByStatusAndUser(status, user, pageable);

            List<Order> content = orderPage.getNumberOfElements() == 0 ?
                    Collections.emptyList() : orderPage.getContent();

        return ordersPageResponse(content, orderPage);

    }


    @Override
    public PagedOrderByStatusResponse<OrderResponseDTO> adminGetOrderByStatus(String status,User user,
                                                                              int page, int size) {

        checkUserRole(ERole.ADMIN, user);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

        Page<Order> orderPage = orderRepository.findByStatus(status, pageable);

        List<Order> content = orderPage.getNumberOfElements() == 0 ?
                Collections.emptyList() : orderPage.getContent();

        return ordersPageResponse(content, orderPage);

    }

    @Override
    public void updateOrderStatus(Long orderId, UpdateOrderRequest orderRequest) {
        User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        checkUserRole(ERole.ADMIN, user);
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if(orderOptional.isEmpty()) throw new ResourceNotFoundException("Incorrect parameter; order with id, " + orderId + " does not exist");
        Order order = orderOptional.get();
        order.setStatus(orderRequest.getStatus());
        orderRepository.save(order);
    }

    @Override
    public void userConfirmOrderStatus(Long orderId) {
        User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        checkUserRole(ERole.USER, user);
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if(orderOptional.isEmpty()) throw new ResourceNotFoundException("Incorrect parameter; order with id, " + orderId + " does not exist");
        Order order = orderOptional.get();
        order.setStatus("DELIVERED");
        orderRepository.save(order);
    }

    @Override
    public List<OrderResponseDTO> getAllOrdersAdmin() {
        List<OrderResponseDTO> paginatedOrderResponseDTOs = orderRepository.findAll().stream().map(order -> modelMapper.map(order, OrderResponseDTO.class))
                .collect(Collectors.toList());
        return paginatedOrderResponseDTOs;
    }

    public PagedOrderByStatusResponse<OrderResponseDTO> ordersPageResponse(List<Order> orders,
                                                                           Page<Order> orderPage) {
        List<OrderResponseDTO> orderResponseList = orders.stream().map(order -> {
            OrderResponseDTO orderResponseDTO = modelMapper.map(order, OrderResponseDTO.class);
            orderResponseDTO.setOrderedBy(modelMapper.map(order.getUser(), UserDTO.class));
            return orderResponseDTO;
        }).collect(Collectors.toList());

        return new PagedOrderByStatusResponse<>(
                orderResponseList, orderPage.getNumber(), orderPage.getNumberOfElements(), orders.size(),
                orderPage.getTotalPages(), orderPage.isLast()
        );

    }

    @Override
    public PagedOrderByStatusResponse<OrderResponseDTO> adminGetOrderByUser(Long userId, Integer page, Integer size) {
        User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        User userWithId = userService.findUserById(userId);
        checkUserRole(ERole.ADMIN, user);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Order> orderPage = orderRepository.findByUser(userWithId, pageable);
        List<Order> content = orderPage.getNumberOfElements() == 0 ? Collections.emptyList() : orderPage.getContent();
        return ordersPageResponse(content, orderPage);
    }

    @Override
    public PagedOrderByStatusResponse<OrderResponseDTO> userGetOrderByUser(Integer page, Integer size) {
        User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        checkUserRole(ERole.USER, user);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Order> orderPage = orderRepository.findByUser(user, pageable);
        List<Order> content = orderPage.getNumberOfElements() == 0 ? Collections.emptyList() : orderPage.getContent();
        return ordersPageResponse(content, orderPage);
    }

    public void checkUserRole(ERole role, User user){
        Role userRole = roleRepository.findByName(role)
                .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));
        Role role1 = user.getRoles().get(0);

        if(role1 != userRole){
            throw new BadRequestException("You don't have access to this link",HttpStatus.UNAUTHORIZED);
        }

    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
}
