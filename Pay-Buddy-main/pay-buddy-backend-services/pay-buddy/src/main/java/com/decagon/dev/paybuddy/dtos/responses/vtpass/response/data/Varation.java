package com.decagon.dev.paybuddy.dtos.responses.vtpass.response.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ikechi Ucheagwu
 * @created 08/03/2023 - 10:20
 * @project SikabethWalletAPI
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Varation {
    private String variation_code;
    private String name;
    private String variation_amount;
    private String fixedPrice;
}


