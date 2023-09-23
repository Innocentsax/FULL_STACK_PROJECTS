package com.example.food.pojos;

import com.example.food.dto.ProductDto;
import com.example.food.restartifacts.BaseResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse extends BaseResponse {

        private List<ProductDto> products;

        public void setProductDto(List<ProductDto> productDto) {
                this.products=productDto;
        }
}
