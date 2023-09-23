package com.example.food.pojos;

import com.example.food.restartifacts.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateOrderStatusResponse extends BaseResponse {

    private String message;
}
