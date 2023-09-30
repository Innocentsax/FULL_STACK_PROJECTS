package com.decagon.dev.paybuddy.dtos.responses.vtpass.response.data;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TransactionDate {
    private String date;
    private Integer timezone_type;
    private String timezone;
}
