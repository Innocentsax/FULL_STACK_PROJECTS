package com.decagon.safariwebstore.dto;

import com.decagon.safariwebstore.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private double price;
    private String description;
    private List<Category> category;
    private List<SubCategory> subCategory;
    private List<Size> sizes;
    private List<Color> colors;
    private List<ProductImage> productImages;

    public ProductDTO(Long id, String name, double price, String description, List<Category> category, List<SubCategory> subCategory) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.subCategory = subCategory;
    }
}
