package com.example.cedarxpressliveprojectjava010.dto.frontEndDto;
import lombok.*;
import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {
    private Long id;
    private String img;
    private String product;
    private BigDecimal price;
    private int quantity;
    private double total;
}