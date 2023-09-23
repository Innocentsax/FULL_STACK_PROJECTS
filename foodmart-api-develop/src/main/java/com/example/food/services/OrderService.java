package com.example.food.services;


import com.example.food.Enum.OrderStatus;
import com.example.food.pojos.OrderResponse;
import com.example.food.pojos.ViewAllOrderResponse;
import com.example.food.restartifacts.BaseResponse;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    OrderResponse viewParticularOrder(Long orderId);

    BaseResponse viewOrderHistory();

    ViewAllOrderResponse viewAllOrders(Long userId);

    BaseResponse updateStatusOfAnOrder(Long orderId, OrderStatus newStatus) ;
}
