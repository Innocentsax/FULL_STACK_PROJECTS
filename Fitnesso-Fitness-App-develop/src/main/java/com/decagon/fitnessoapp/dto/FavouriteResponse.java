package com.decagon.fitnessoapp.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavouriteResponse {
    private Long id;
    private Long productId;
    private Long personId;
    private String productName;
    private String prouctName;
    private BigDecimal price;
    private String description;
    private Long stock;
    private String productType;
    private String image;
    private Integer durationInHourPerDay;
    private Integer durationInDays;
    private Integer quantity;


}
