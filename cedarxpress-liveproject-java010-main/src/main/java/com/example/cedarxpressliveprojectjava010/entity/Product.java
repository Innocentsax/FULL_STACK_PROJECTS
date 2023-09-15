package com.example.cedarxpressliveprojectjava010.entity;

import lombok.*;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Product extends Base{
    @NotNull(message = "Product name field is empty")
    private String productName;
    @NotNull(message = "Description field is empty")
    private String description;
    @NotNull(message = "Price field is empty")
    private double price;
    @OneToMany
    @ToString.Exclude
    private List<ImageUrl> images = new ArrayList<>();
    @ManyToOne
    private SubCategory subCategory;
    @ManyToOne
    private Category category;
}