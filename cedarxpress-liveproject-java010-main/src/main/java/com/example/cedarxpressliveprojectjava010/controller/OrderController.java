package com.example.cedarxpressliveprojectjava010.controller;

import com.example.cedarxpressliveprojectjava010.dto.OrderDto;
import com.example.cedarxpressliveprojectjava010.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin("*")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{userId}")
    public List<OrderDto> getOrders(@PathVariable("userId") long userId){
        //TODO: test end point
        return orderService.viewUserOrders(userId);
    }

    @GetMapping("/{userId}/{orderId}")
    public ResponseEntity<OrderDto> getSingleOrder(@PathVariable("userId") long userId,
                                                   @PathVariable("orderId") long orderId){
        //TODO: test end point
        return orderService.viewParticularUserOrder(userId, orderId);
    }

    @GetMapping("/{status}")
    public List<OrderDto> fetchByStatus(@PathVariable("status") String stats){
        return orderService.viewOrderByStatus(stats);
    }


}
