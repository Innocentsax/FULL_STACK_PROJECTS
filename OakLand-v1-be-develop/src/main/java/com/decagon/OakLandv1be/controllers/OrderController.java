package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.*;
import com.decagon.OakLandv1be.enums.PickupStatus;
import com.decagon.OakLandv1be.services.CartService;

import com.decagon.OakLandv1be.dto.AddressRequestDto;
import com.decagon.OakLandv1be.dto.CheckoutDto;
import com.decagon.OakLandv1be.dto.CheckoutResponseDto;
import com.decagon.OakLandv1be.dto.OrderResponseDto;
import com.decagon.OakLandv1be.enums.DeliveryStatus;
import com.decagon.OakLandv1be.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;

    @GetMapping("customer/order-history")
    public ResponseEntity<List<OrderResponseDto>> viewOrderHistory(@RequestParam int pageNo,
                                                                   @RequestParam int pageSize){
        return ResponseEntity.ok(orderService.viewOrderHistory(pageNo, pageSize));
    }

    @GetMapping("customer/order/{orderId}")
    public ResponseEntity<OrderResponseDto> viewASingleOrder (@PathVariable("orderId") Long orderId){
       return new ResponseEntity<>(orderService.viewAParticularOrder(orderId), HttpStatus.OK);
    }

    @GetMapping("admin/orders")
    public ResponseEntity<Page<AdminOrderResponseDto>> viewAllOrdersPaginated(
                        @RequestParam(defaultValue = "0") Integer pageNo,
                        @RequestParam(defaultValue = "16") Integer pageSize,
                        @RequestParam(defaultValue = "id") String sortBy,
                        @RequestParam(defaultValue = "false") boolean isAscending) {
        return new ResponseEntity<>(orderService.viewAllOrdersPaginated(pageNo, pageSize, sortBy, isAscending), HttpStatus.OK);
    }

    @PostMapping("customer/order/new")
    public ResponseEntity<String> saveNewOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return new ResponseEntity<>(orderService.saveOrder(orderRequestDto), HttpStatus.OK);
    }

    @GetMapping("admin/order/delivery-status")
    public ResponseEntity<Page<OrderResponseDto>> orderByStatus(
            @RequestParam DeliveryStatus status,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize    ){
        return ResponseEntity.ok(orderService.getOrderByDeliveryStatus(status, pageNo, pageSize));
    }

    @GetMapping("customer/order/pickup-status")
    public ResponseEntity<Page<OrderResponseDto>> orderByPickupStatus(
            @RequestParam PickupStatus status,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "1") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy ){
        return ResponseEntity.ok(orderService.getCustomerOrderByPickupStatus(status, pageNo, pageSize));
    }
}