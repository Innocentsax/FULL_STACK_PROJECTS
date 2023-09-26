package com.example.money_way.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class VTPassResponseDto {

    private String transactionId;
    private String status;
    private String product_name;
    private String unique_element;
    private double unit_price;
    private int quantity;
    private String type;
    private String email;
    private String phone;

}
