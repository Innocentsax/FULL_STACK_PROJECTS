package com.example.food.services.serviceImpl;

import com.example.food.Enum.OrderStatus;
import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.dto.CartItemDto;
import com.example.food.model.Order;
import com.example.food.model.OrderedItem;
import com.example.food.model.Users;
import com.example.food.pojos.OrderResponse;
import com.example.food.pojos.UpdateOrderStatusResponse;
import com.example.food.pojos.ViewAllOrderResponse;
import com.example.food.pojos.ViewOrderHistoryResponse;
import com.example.food.repositories.OrderRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.OrderService;
import com.example.food.util.ResponseCodeUtil;
import com.example.food.util.UserUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final UserUtil userUtil;
    private final ResponseCodeUtil responseCodeUtil = new ResponseCodeUtil();
    private Users getLoggedInUser() {
        String authentication = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(authentication)
                .orElseThrow(() -> new RuntimeException("User not authorized"));
    }

    @Override
    public OrderResponse viewParticularOrder(Long orderId) {
        OrderResponse response = new OrderResponse();
        try {
            Users user = getLoggedInUser();
            Order order = orderRepository.findOrderByUserAndId(user, orderId);
            List<CartItemDto> orderedItems = new ArrayList<>();

            for (OrderedItem item : order.getOrderedItem()) {
                ObjectMapper objectMapper = new ObjectMapper();
                CartItemDto cartItemDto = objectMapper.convertValue(item, CartItemDto.class);
                orderedItems.add(cartItemDto);
            }

            response = OrderResponse.builder()
                    .id(order.getId())
                    .imageUrl(order.getImageUrl())
                    .createdAt(order.getCreatedAt())
                    .modifiedAt(null)
                    .quantity(order.getQuantity())
                    .orderStatus(order.getOrderStatus())
                    .cartItemDtoList(orderedItems)
                    .address(order.getAddress())
                    .paymentMethod(order.getPaymentMethod())
                    .deliveryFee(order.getDeliveryFee())
                    .discount(order.getDiscount())
                    .deliveryMethod(order.getDeliveryMethod())
                    .totalOrderPrice(order.getTotalOrderPrice())
                    .build();
            response.setCode(0);

            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.SUCCESS, "These are the order details");
        } catch (Exception e) {
            log.error("An error occurred: {}", e.getMessage());
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.ERROR, "Sorry! An Error Occurred");
        }


    }

    @Override
    public ViewAllOrderResponse viewAllOrders(Long userId) {
        ViewAllOrderResponse allOrderResponse = new ViewAllOrderResponse();
        String email = userUtil.getAuthenticatedUserEmail();
        Optional<Users> users = userRepository.findByEmail(email);

        if (users.isEmpty()) {
            return responseCodeUtil.updateResponseData(allOrderResponse, ResponseCodeEnum.USER_NOT_FOUND);
        }
        List<Order> myOrder = orderRepository.findAllByUser_Email(email);

        if (myOrder.isEmpty()) {
            allOrderResponse.setCode(-1);
            allOrderResponse.setListOfOrders(new ArrayList<>());
            return responseCodeUtil.updateResponseData(allOrderResponse, ResponseCodeEnum.ORDERS_NOT_FOUND);

        } else {
            allOrderResponse.setCode(0);
            allOrderResponse.setListOfOrders(myOrder);
            allOrderResponse.setDescription("You have made an order");
            return responseCodeUtil.updateResponseData(allOrderResponse, ResponseCodeEnum.SUCCESS);
        }
    }


    @Override
    public BaseResponse viewOrderHistory() {

        String email = userUtil.getAuthenticatedUserEmail();
        Optional<Users> users = userRepository.findByEmail(email);
        List<Order> orders = orderRepository.findAllByUserOrderByIdDesc(users.get());
        log.info("Orders are {} ", orders);

        ViewOrderHistoryResponse viewOrderHistoryResponse = ViewOrderHistoryResponse.builder()
                .orderList(orders)
                .build();

        return responseCodeUtil.updateResponseData(viewOrderHistoryResponse, ResponseCodeEnum.SUCCESS);
    }


    @Override
    public BaseResponse updateStatusOfAnOrder(Long orderId, OrderStatus newStatus) {
        String email = userUtil.getAuthenticatedUserEmail();
        Users user = userRepository.findByEmail(email).orElseThrow(RuntimeException::new);
        UpdateOrderStatusResponse orderStatusResponse = new UpdateOrderStatusResponse();

        if (orderRepository.findById(orderId).isPresent()) {
            Order order = orderRepository.findById(orderId).get();
            order.setOrderStatus(newStatus);
            orderRepository.save(order);
            orderStatusResponse.setCode(0);
            orderStatusResponse.setMessage("Order status change was successful!");
            return responseCodeUtil.updateResponseData(orderStatusResponse, ResponseCodeEnum.SUCCESS);
        }
        orderStatusResponse.setCode(-1);
        orderStatusResponse.setMessage("Sorry, Order status change was unsuccessful!");
        return responseCodeUtil.updateResponseData(orderStatusResponse, ResponseCodeEnum.ERROR);
    }
}
