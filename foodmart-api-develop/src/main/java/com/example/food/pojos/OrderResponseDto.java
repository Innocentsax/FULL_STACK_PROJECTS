package com.example.food.pojos;

import com.example.food.dto.OrderDto;
import com.example.food.restartifacts.BaseResponse;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class OrderResponseDto extends BaseResponse {
    private OrderDto orderDto;
}

