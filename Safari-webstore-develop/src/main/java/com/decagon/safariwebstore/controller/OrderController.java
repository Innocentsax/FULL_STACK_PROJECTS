package com.decagon.safariwebstore.controller;

import com.decagon.safariwebstore.dto.OrderResponseDTO;
import com.decagon.safariwebstore.model.Order;
import com.decagon.safariwebstore.model.User;
import com.decagon.safariwebstore.payload.request.UpdateOrderRequest;
import com.decagon.safariwebstore.payload.response.PagedOrderByStatusResponse;
import com.decagon.safariwebstore.service.OrderService;
import com.decagon.safariwebstore.service.UserService;
import com.decagon.safariwebstore.utils.JWTUtil;
import com.decagon.safariwebstore.utils.MethodUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final JWTUtil jwtUtil;

    @GetMapping("/{orderId}")
    @Secured({"ADMIN","USER"})
    public ResponseEntity<OrderResponseDTO> viewParticularOrder(@PathVariable Long orderId) {
        return new ResponseEntity<>(orderService.getOrder(orderId), HttpStatus.OK);
    }

    @GetMapping("/admin")
    @Secured("ADMIN")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrdersAdmin() {
        return new ResponseEntity<>(orderService.getAllOrdersAdmin(), HttpStatus.OK);
    }

    @GetMapping("/user/status")
    @Secured("USER")
    public ResponseEntity<PagedOrderByStatusResponse<OrderResponseDTO>> getOrdersByStatusUser(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "30") Integer size,
            @RequestParam(name = "status") String status, HttpServletRequest request){

        String upCase = status.toUpperCase();
        String jwt = MethodUtils.parseJwt(request);
        String email = jwtUtil.extractUserName(jwt);
        User user = userService.findUserByEmail(email);
        PagedOrderByStatusResponse<OrderResponseDTO> orderByStatus = orderService.userGetOrderByStatus(upCase, user, page, size);
        return ResponseEntity.ok(orderByStatus);

    }

    @GetMapping("/admin/status")
    @Secured("ADMIN")
    public ResponseEntity<PagedOrderByStatusResponse<OrderResponseDTO>> getOrdersByStatusAdmin(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "30") Integer size,
            @RequestParam(name = "status") String status, HttpServletRequest request){

        String upCase = status.toUpperCase();
        String jwt = MethodUtils.parseJwt(request);
        String email = jwtUtil.extractUserName(jwt);
        User user = userService.findUserByEmail(email);
        PagedOrderByStatusResponse<OrderResponseDTO> orderByStatus = orderService.adminGetOrderByStatus(upCase, user, page, size);
        return ResponseEntity.ok(orderByStatus);

    }

    @GetMapping("/user")
    @Secured("USER")
    public ResponseEntity<PagedOrderByStatusResponse<OrderResponseDTO>> userGetOrdersByUser(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "30") Integer size){
        return ResponseEntity.ok(orderService.userGetOrderByUser(page, size));

    }

    @GetMapping("/admin/{userId}")
    @Secured("ADMIN")
    public ResponseEntity<PagedOrderByStatusResponse<OrderResponseDTO>> adminGetOrdersByUser(@PathVariable Long userId,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "30") Integer size){
        return ResponseEntity.ok(orderService.adminGetOrderByUser(userId, page, size));

    }

    @PutMapping("/admin/{orderId}")
    @Secured("ADMIN")
    public ResponseEntity<?> adminUpdateOrderStatus(@PathVariable Long orderId, @RequestBody UpdateOrderRequest orderRequest){
        orderService.updateOrderStatus(orderId, orderRequest);
        return new ResponseEntity<>("Product Status Updated Successfully", HttpStatus.OK);
    }

    @PutMapping("/user/{orderId}")
    @Secured({"ADMIN","USER"})
    public ResponseEntity<?> userConfirmOrder(@PathVariable Long orderId){
        orderService.userConfirmOrderStatus(orderId);
        return new ResponseEntity<>("Product Status Updated Successfully", HttpStatus.OK);
    }


}
