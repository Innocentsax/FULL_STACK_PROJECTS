package com.decagon.kindredhairapigrp1.models;

import com.decagon.kindredhairapigrp1.models.Basemodel.BaseModel;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product extends BaseModel {

    @NotBlank(message = "Name field  cannot be empty")
    private String name;
    @NotBlank(message = "Price field  cannot be empty")
    private Integer price;
    @NotBlank(message = "Size field  cannot be empty")
    private String size;
    @Column(length = 10000)
    @Type(type = "text")
    @NotBlank(message = "Description field  cannot be empty")
    private String description;
    @Column(length = 5000)
    @Type(type = "text")
    @NotBlank(message = "Ingredients field  cannot be empty")
    private String ingredients;
    @NotBlank(message = "available field  cannot be empty")
    private boolean available;
    @NotBlank(message = "productURL field  cannot be empty")
    private String productURL;
    @NotBlank(message = "ImageUrl field  cannot be empty")
    @Column(length = 500)
    private String imageURL;
    @NotBlank(message = "brandName field  cannot be empty")
    private String brand;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Feedback> feedbacks;
    @Column (length = 500)
    private String category;
    @Column (length = 500)
    private String searchIngredients;
    @Column (length = 500)
    private String searchDescription;
    private long shopifyID;
    private boolean shopifyUpdateStatus;

    public Product(String name, Integer price, String size, String description, String ingredients, boolean available, String productURL, String imageURL, String brand) {
        this.name = name;
        this.price = price;
        this.size = size;
        this.description = description;
        this.ingredients = ingredients;
        this.available = available;
        this.productURL = productURL;
        this.imageURL = imageURL;
        this.brand = brand;

    }
}
