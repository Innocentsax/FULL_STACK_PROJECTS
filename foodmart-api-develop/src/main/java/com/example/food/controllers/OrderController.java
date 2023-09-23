package com.example.food.controllers;


import com.example.food.Enum.OrderStatus;
import com.example.food.pojos.OrderResponse;
import com.example.food.pojos.ViewAllOrderResponse;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@RestController
public class OrderController {


    private final OrderService orderService;

    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderResponse> viewDetailsOfAParticularOrder(@PathVariable Long orderId){
        return new ResponseEntity<>(orderService.viewParticularOrder(orderId), HttpStatus.ACCEPTED);
    }
    @GetMapping("/order")
    public BaseResponse viewOrderHistory(){
        return orderService.viewOrderHistory();
    }

    @GetMapping("/viewOrder/{userId}")
    public ResponseEntity<ViewAllOrderResponse> viewAllOrders(@PathVariable Long userId) {
        return new ResponseEntity<>(orderService.viewAllOrders(userId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateOrder/{orderId}/status")
    public ResponseEntity<BaseResponse> updateStatusOfAnOrder(@PathVariable Long orderId, @RequestBody OrderStatus newStatus) {
        //Order updatedOrder = orderService.updateStatusOfAnOrder(orderId, request.getStatus());
        return new ResponseEntity<>(orderService.updateStatusOfAnOrder(orderId, newStatus), HttpStatus.OK);
    }
}
