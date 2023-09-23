package com.example.food.dto;

import com.example.food.model.Order;
import com.example.food.restartifacts.BaseResponse;
import lombok.Data;

@Data
public class OrderDto extends BaseResponse {
    private Order order;
}
