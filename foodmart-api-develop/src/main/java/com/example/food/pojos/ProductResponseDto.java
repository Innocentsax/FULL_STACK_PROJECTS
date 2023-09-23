package com.example.food.pojos;

import com.example.food.dto.ProductDto;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.ProductService;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ProductResponseDto extends BaseResponse {
    private ProductDto productDto;
    private ProductService productService;
}
