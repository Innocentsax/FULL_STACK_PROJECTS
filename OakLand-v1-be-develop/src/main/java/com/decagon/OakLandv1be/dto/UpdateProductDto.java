package com.decagon.OakLandv1be.dto;

import com.decagon.OakLandv1be.entities.Category;
import com.decagon.OakLandv1be.entities.SubCategory;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UpdateProductDto {
    @NotBlank(message = "Name may not be blank")
    private String name;

    @NotBlank(message = "price cannot be blank")
    private Double price;

    @NotBlank (message = "imageUrl may not be blank" )
    private String imageUrl;

    @NotBlank(message = "The availableQty cannot be blank")
    private Integer availableQty;

    @NotBlank(message = "The subCategory cannot be blank")
    private String subCategory;

    @NotBlank(message = "The colour cannot be blank" )
    private String color;

    @NotBlank(message = "The description cannot be blank")
    private String description;

}
