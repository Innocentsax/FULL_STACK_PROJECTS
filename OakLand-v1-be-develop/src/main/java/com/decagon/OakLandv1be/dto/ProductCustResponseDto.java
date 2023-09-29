package com.decagon.OakLandv1be.dto;

import com.decagon.OakLandv1be.entities.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCustResponseDto {

    private String name;

    private Double price;
    private Integer availableQty;

    private String imageUrl;

    private String color;

    private String description;
    private SubCategory subCategory;
}
