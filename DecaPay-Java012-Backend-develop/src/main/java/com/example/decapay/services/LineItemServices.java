package com.example.decapay.services;

import com.example.decapay.pojos.requestDtos.LineItemRequestDto;
import com.example.decapay.pojos.responseDtos.LineItemResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface LineItemServices {

    LineItemResponseDto createLineItem(LineItemRequestDto lineItemRequestDto);

    ResponseEntity<LineItemResponseDto> updateLineItem(LineItemRequestDto lineItemRequestDto, Long lineItemId);

    List<LineItemResponseDto> getLineItems();

    Boolean deleteLineItem(Long id);

}