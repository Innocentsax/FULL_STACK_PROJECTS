package com.example.cedarxpressliveprojectjava010.service;
import com.example.cedarxpressliveprojectjava010.dto.frontEndDto.CartDto;
import org.springframework.http.ResponseEntity;
public interface CartService {
    ResponseEntity<CartDto> findCartByUser();
}