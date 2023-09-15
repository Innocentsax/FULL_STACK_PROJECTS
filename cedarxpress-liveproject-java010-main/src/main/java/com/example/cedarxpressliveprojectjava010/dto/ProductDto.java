package com.example.cedarxpressliveprojectjava010.dto;

import lombok.*;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    @NotNull(message = "Product name field is empty")
    private String productName;
    @NotNull(message = "Description field is empty")
    private String description;
    @NotNull(message = "Price field is empty")
    private double price;
    @NotNull(message = "Sub category field is empty")
    private String subCategory;
    @NotNull(message = "Category field is empty")
    private String category;
}

