package com.decagon.safariwebstore.controller;

import com.decagon.safariwebstore.dto.OrderRequestDTO;
import com.decagon.safariwebstore.dto.OrderResponseDTO;
import com.decagon.safariwebstore.service.CheckOutService;
import com.decagon.safariwebstore.utils.JWTUtil;
import com.decagon.safariwebstore.utils.MethodUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
@RequestMapping("/api/check-out")
public class CheckOutController {


    private final CheckOutService checkoutService;

    private final JWTUtil jwtUtil;


    @PostMapping()
    @Secured({"ADMIN","USER"})
    public ResponseEntity<OrderResponseDTO> doCheckOut(HttpServletRequest request,
                                        @RequestBody OrderRequestDTO orderRequest){

        String jwt = MethodUtils.parseJwt(request);
        String email = jwtUtil.extractUserName(jwt);
        OrderResponseDTO orderResponseDTO = checkoutService.doCheckout(orderRequest, email);
        return new ResponseEntity<>( orderResponseDTO, HttpStatus.OK);
    }
}
