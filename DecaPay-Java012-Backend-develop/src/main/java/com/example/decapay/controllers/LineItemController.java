package com.example.decapay.controllers;

import com.example.decapay.pojos.requestDtos.LineItemRequestDto;
import com.example.decapay.pojos.responseDtos.LineItemResponseDto;
import com.example.decapay.services.LineItemServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/line-items")
public class LineItemController {

    private final LineItemServices lineItemServices;


    @PostMapping
    public ResponseEntity<LineItemResponseDto> createLineItem(@RequestBody LineItemRequestDto lineItemRequestDto) {
        return new ResponseEntity<>(lineItemServices.createLineItem(lineItemRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{lineItemId}")
    public ResponseEntity<LineItemResponseDto> updateALineItem(@RequestBody LineItemRequestDto lineItemRequestDto, @Valid @PathVariable Long lineItemId) {
        return lineItemServices.updateLineItem(lineItemRequestDto, lineItemId);
    }


    @GetMapping("/listLineItems")
    public ResponseEntity<List<LineItemResponseDto>> getLineItems() {
        List<LineItemResponseDto> lineItems = lineItemServices.getLineItems();
        return new ResponseEntity<>(lineItems, HttpStatus.OK);
    }

    @DeleteMapping("/delete-line-item/{Id}")
    public ResponseEntity<Boolean> deleteLineItem(@PathVariable Long Id){
        return new ResponseEntity<>( lineItemServices.deleteLineItem(Id), HttpStatus.OK);
    }
}
