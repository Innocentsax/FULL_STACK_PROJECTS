package com.example.cedarxpressliveprojectjava010.service;

import com.example.cedarxpressliveprojectjava010.dto.CheckOutDto;
import com.example.cedarxpressliveprojectjava010.dto.OrderDto;
import org.springframework.http.ResponseEntity;

public interface CheckOutService {
    ResponseEntity<OrderDto> makeOrder(CheckOutDto checkOutOneDto);

}
