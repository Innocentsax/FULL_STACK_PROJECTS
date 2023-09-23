package com.decagon.fitnessoapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProductDto implements Comparable<UserProductDto>{
    private Long id;
    private String category;
    private String productName;
    private BigDecimal price;
    private String description;
    private String image;
    private Integer durationInHoursPerDay;
    private Integer durationInDays;
    private Integer quantity;

    @Override
    public int compareTo(@NotNull UserProductDto o) {
        return this.getProductName().compareTo(o.getProductName());
    }
}
