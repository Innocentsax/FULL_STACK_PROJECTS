package com.decagon.fitnessoapp.controller;

import com.decagon.fitnessoapp.dto.CheckOutRequest;
import com.decagon.fitnessoapp.dto.CheckOutResponse;
import com.decagon.fitnessoapp.service.CheckOutService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/checkout")
@AllArgsConstructor
public class CheckOutController {
    private final CheckOutService checkOutService;
// TODO: Review checkout relationship with cart. Cart is suppose to proceed to checkout
    @PreAuthorize("hasAnyRole('ROLE_PREMIUM', 'ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<CheckOutResponse> saveCheckout(@RequestBody @Valid CheckOutRequest checkOutRequest){
        return new ResponseEntity<>(checkOutService.save(checkOutRequest), HttpStatus.CREATED);
    }
}
