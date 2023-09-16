package com.decagon.chompapp.dtos;

import com.decagon.chompapp.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class OrderDtoForStatusUpdate {
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
