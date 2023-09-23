package com.decagon.fitnessoapp.controller;

import com.decagon.fitnessoapp.dto.OrderResponse;
import com.decagon.fitnessoapp.model.product.ORDER_STATUS;
import com.decagon.fitnessoapp.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.List;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("hasAnyRole('ROLE_PREMIUM', 'ROLE_ADMIN')")
    @GetMapping("/viewOrder")
    public ResponseEntity<List<OrderResponse>> viewOrder(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(orderService.getAllOrderByPerson(authentication));
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/viewOrders/{pageNo}")
    public ResponseEntity<List<OrderResponse>> getAllOrders(@PathVariable String pageNo) {
        List<OrderResponse> orderResponses = orderService.getAllOrders(Integer.parseInt(pageNo));
        return new ResponseEntity<>(
                orderResponses, new HttpHeaders(), HttpStatus.OK
        );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/viewOrdersBy/{status}/{pageNo}")
    public ResponseEntity<Page<OrderResponse>> viewOrdersByStatus(@PathVariable(value = "status") ORDER_STATUS status, @PathVariable(value = "pageNo") int pageNo) {
        final Page<OrderResponse> ordersByStatus = orderService.getOrdersByStatus(status, pageNo);
        return ResponseEntity.ok(ordersByStatus);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/admin/cancelOrder/{id}")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable(value = "id") Long orderId) {
        return ResponseEntity.ok().body(orderService.cancelOrder(orderId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/admin/completeOrder/{id}")
    public ResponseEntity<OrderResponse> completeOrder(@PathVariable(value = "id") Long orderId) {
        return ResponseEntity.ok().body(orderService.completeOrder(orderId));
    }
}
