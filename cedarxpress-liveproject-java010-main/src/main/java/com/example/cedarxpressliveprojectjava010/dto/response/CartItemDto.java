package com.example.cedarxpressliveprojectjava010.dto.response;

import com.example.cedarxpressliveprojectjava010.dto.ProductDto;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CartItemDto {
    private ProductDto productDto;
    private int unit;
}
