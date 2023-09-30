package com.decagon.dev.paybuddy.dtos.requests;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDate {
    private String date;
    private Integer timezone_type;
    private String timezone;
}
