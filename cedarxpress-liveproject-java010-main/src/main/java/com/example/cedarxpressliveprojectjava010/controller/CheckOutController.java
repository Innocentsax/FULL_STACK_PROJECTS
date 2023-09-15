package com.example.cedarxpressliveprojectjava010.controller;

import com.example.cedarxpressliveprojectjava010.dto.CheckOutDto;
import com.example.cedarxpressliveprojectjava010.dto.OrderDto;
import com.example.cedarxpressliveprojectjava010.service.CheckOutService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
@CrossOrigin("*")
public class CheckOutController {
private CheckOutService checkOutService;

    @PostMapping("/customer/checkout")
    public ResponseEntity<OrderDto> checkOut(@RequestBody CheckOutDto checkOutDto){
        return checkOutService.makeOrder(checkOutDto);
    }
}
