package com.example.food.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class ProductSearchDto {
    private String sortDirection;
    private String sortBy;
    private String filter;
    private int pageNumber;
    private int pageSize;

    public ProductSearchDto() {
        this.sortDirection = "asc";
        this.sortBy = "productName";
        this.filter = "";
        this.pageNumber = 0;
        this.pageSize = 10;
    }
}
