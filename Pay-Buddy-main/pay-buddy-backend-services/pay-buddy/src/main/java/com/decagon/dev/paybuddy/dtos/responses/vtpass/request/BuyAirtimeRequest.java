package com.decagon.dev.paybuddy.dtos.responses.vtpass.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyAirtimeRequest {
    //Mandatory field it would be set at runtime from backend
    private String request_id; //https://www.vtpass.com/documentation/how-to-generate-request-id/

    @NotBlank(message = "serviceID is mandatory")
    @Schema(example = "mtn, airtel, etc as specified by VTPASS")
    private String serviceID;

    @NotBlank(message = "amount is mandatory")
    @Schema(example = "100.00 i.e. the amount of the variation (as specified in the GET VARIATIONS endpoint as variation_amount) optional as variation_code is mandatory")
    private BigDecimal amount;

    //for test use this 08011111111 all other phone number would fail for sandbox mode
    @NotBlank(message = "phone is mandatory")
    @Schema(example = "for test use this 08011111111 - i.e the phone number of the customer or recipient of this service")
    private String phone;
}
