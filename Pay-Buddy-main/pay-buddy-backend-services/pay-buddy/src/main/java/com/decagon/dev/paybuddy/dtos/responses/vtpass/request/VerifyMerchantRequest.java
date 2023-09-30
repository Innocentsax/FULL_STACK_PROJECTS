package com.decagon.dev.paybuddy.dtos.responses.vtpass.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class VerifyMerchantRequest {
    private  String billersCode;
    private  String serviceID ;
    private String type;
}
