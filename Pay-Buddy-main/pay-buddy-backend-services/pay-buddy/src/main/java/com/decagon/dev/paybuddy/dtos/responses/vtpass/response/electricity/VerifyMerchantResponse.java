package com.decagon.dev.paybuddy.dtos.responses.vtpass.response.electricity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VerifyMerchantResponse {
    private String code;
    private Content content;
}
