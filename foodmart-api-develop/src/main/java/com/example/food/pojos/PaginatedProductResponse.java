package com.example.food.pojos;

import com.example.food.model.Product;
import com.example.food.restartifacts.BaseResponse;
import lombok.*;
import org.springframework.data.domain.Page;


@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
public class PaginatedProductResponse extends BaseResponse {
    private Page<Product> productList;
}