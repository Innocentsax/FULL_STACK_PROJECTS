package com.decagon.fitnessoapp.service;

import com.decagon.fitnessoapp.dto.OrderResponse;
import com.decagon.fitnessoapp.model.product.ORDER_STATUS;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface OrderService {
   // OrderResponse getOrder(Authentication authentication);

    //Page<OrderResponse> getAllOrders(int pageNo);
    List<OrderResponse> getAllOrderByPerson(Authentication authentication);

    List<OrderResponse> getAllOrders(Integer pageNo);

    Page<OrderResponse> getOrdersByStatus(ORDER_STATUS status, int pageNo);

    OrderResponse cancelOrder(Long orderId);

    OrderResponse completeOrder(Long orderId);
}
