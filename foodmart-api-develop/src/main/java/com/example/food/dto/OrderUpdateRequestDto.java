package com.example.food.dto;

import com.example.food.Enum.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Enumerated;


@Data
@Builder
@AllArgsConstructor
public class OrderUpdateRequestDto {

    @Enumerated
    private OrderStatus orderStatus;


}
