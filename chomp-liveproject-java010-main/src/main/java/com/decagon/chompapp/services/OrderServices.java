package com.decagon.chompapp.services;

import com.decagon.chompapp.dtos.OrderDto;
import com.decagon.chompapp.dtos.OrderDtoForStatusUpdate;
import com.decagon.chompapp.dtos.OrderResponse;
import com.decagon.chompapp.dtos.OrderResponseDto;
import org.springframework.http.ResponseEntity;

public interface OrderServices {

    ResponseEntity<OrderResponse> viewOrderHistoryForAPremiumUser (int pageNo, int pageSize, String sortBy, String sortDir);

    ResponseEntity<OrderResponseDto> updateOrderStatus (Long orderId, OrderDtoForStatusUpdate orderDtoForStatusUpdate);
}
