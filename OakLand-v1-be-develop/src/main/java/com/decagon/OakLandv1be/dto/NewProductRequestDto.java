package com.decagon.OakLandv1be.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewProductRequestDto {
    @NotBlank(message = "Product name cannot be blank")
    private String name;

    @DecimalMin(value="0.0", message="Field Price cannot be blank")
    private Double price;

    @NotBlank(message = "Field ImageUrl cannot be blank")
    private String imageUrl;

    @Range(min=0, message = "Field Available Quantity cannot be blank")
    private Integer availableQty;

    @NotBlank(message="Field SubCategory cannot be null")
    private String subCategory;

    @NotBlank(message = "Field Color cannot be blank")
    private String color;

    @NotBlank(message = "Field Description cannot be blank")
    private String description;

}
