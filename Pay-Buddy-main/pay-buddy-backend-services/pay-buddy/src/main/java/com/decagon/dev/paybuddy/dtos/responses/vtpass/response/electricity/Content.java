package com.decagon.dev.paybuddy.dtos.responses.vtpass.response.electricity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Content {
    private Transaction transactions;
    private String Customer_Name;
    private String Meter_Number;
    private String Business_Unit;
    private String Address;
    private String Customer_Arrears;
}
