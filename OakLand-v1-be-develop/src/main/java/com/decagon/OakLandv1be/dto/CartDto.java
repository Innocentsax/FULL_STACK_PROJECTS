package com.decagon.OakLandv1be.dto;

import com.decagon.OakLandv1be.entities.Item;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartDto {
    private List<Item> items;
    private BigDecimal total;
}
