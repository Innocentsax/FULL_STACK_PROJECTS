package com.example.food.pojos;

import com.example.food.restartifacts.BaseResponse;
import lombok.*;

@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
public class CreateProductResponse extends BaseResponse {
        private String productName;
}
