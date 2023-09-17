package com.decagon.safariwebstore.dto;

import com.decagon.safariwebstore.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubCategoryDTO {

    private String name;

    private Category category;

}
