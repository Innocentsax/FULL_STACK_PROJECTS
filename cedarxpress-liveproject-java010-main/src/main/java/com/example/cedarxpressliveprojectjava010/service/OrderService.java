package com.example.cedarxpressliveprojectjava010.service;

import com.example.cedarxpressliveprojectjava010.dto.OrderDto;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface OrderService {
    ResponseEntity<OrderDto> viewParticularUserOrder(long userId, long orderId);
    List<OrderDto> viewUserOrders(long userId);
    List<OrderDto> viewOrderByStatus(String status);

}
