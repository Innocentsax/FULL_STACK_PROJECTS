package com.example.cedarxpressliveprojectjava010.dto;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ViewProductDto {
    private String productName;
    private String description;
    private double price;
}
