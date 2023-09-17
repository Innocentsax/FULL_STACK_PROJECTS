package com.decagon.safariwebstore.payload.request;

import com.decagon.safariwebstore.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String name;

    private double price;

    private String description;

    private List<String> category;

    private List<String> subCategory;

    private List<String> sizes;

    private List<String> colors;

    private List<String> productImages;

    public void setName(String name) {
        this.name = name.toLowerCase();
    }

}
