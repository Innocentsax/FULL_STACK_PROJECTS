package com.decagon.fitnessoapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String category;
    private String productName;
    private BigDecimal price;
    private String description;
    private Long stock;
    private String ProductType;
    private String image;
    private Integer monthlySubscription;
    private Integer quantity;
}
