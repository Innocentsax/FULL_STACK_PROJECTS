package com.decagon.OakLandv1be.services;


import com.decagon.OakLandv1be.dto.AdminOrderResponseDto;
import com.decagon.OakLandv1be.dto.OrderRequestDto;
import com.decagon.OakLandv1be.dto.OrderResponseDto;
import com.decagon.OakLandv1be.enums.DeliveryStatus;
import com.decagon.OakLandv1be.enums.PickupStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    List<OrderResponseDto> viewOrderHistory(int pageNo, int pageSize);
    OrderResponseDto viewAParticularOrder(Long orderId);
    Page<AdminOrderResponseDto> viewAllOrdersPaginated(Integer pageNo, Integer pageSize, String sortBy, boolean isAscending);

    String saveOrder(OrderRequestDto orderRequestDto);

    Page<OrderResponseDto> getOrderByDeliveryStatus(DeliveryStatus status, Integer pageNo, Integer pageSize);

    Page<OrderResponseDto> getCustomerOrderByPickupStatus(PickupStatus status, Integer pageNo, Integer pageSize);
}
