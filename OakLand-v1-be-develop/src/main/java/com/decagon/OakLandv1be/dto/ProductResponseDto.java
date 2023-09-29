package com.decagon.OakLandv1be.dto;

import com.decagon.OakLandv1be.entities.SubCategory;
import lombok.Builder;
import lombok.Data;



@Data
@Builder
public class ProductResponseDto {
    private Long id;

    private String name;

    private String price;

    private Integer sales;

    private String imageUrl;

    private Integer availableQty;

    private SubCategory subCategory;

    private String color;

    private String description;
}
