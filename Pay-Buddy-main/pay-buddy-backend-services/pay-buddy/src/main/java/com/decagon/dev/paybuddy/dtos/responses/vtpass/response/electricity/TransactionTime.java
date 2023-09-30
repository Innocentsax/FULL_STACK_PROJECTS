package com.decagon.dev.paybuddy.dtos.responses.vtpass.response.electricity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionTime {
    private  String date;
    private String timezone_type;
    private String timezone;
}
