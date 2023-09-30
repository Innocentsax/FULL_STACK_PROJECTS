package com.decagon.dev.paybuddy.dtos.responses.vtpass.response.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ikechi Ucheagwu
 * @created 08/03/2023 - 14:30
 * @project SikabethWalletAPI
 * /

/**
 * <a href="https://www.vtpass.com/documentation/mtn-data/">...</a>
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyDataPlanResponse {
    private String code;
    public String response_description;
    public String requestId;
    public String amount;
    public String purchasedCode;
}
