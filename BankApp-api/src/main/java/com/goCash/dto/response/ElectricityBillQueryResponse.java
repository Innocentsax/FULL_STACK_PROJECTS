package com.goCash.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ElectricityBillQueryResponse {
    @JsonProperty(value = "code")
    private String code;
    private Content content;
    @Data
    public static class Content {
        @JsonProperty(value = "Customer_Name")
        private String customerName;

        @JsonProperty(value = "Meter_Number")
        private String meterNumber;

        @JsonProperty(value = "Business_Unit")
        private String businessUnit;

        @JsonProperty(value = "Address")
        private String address;

        @JsonProperty(value = "Customer_Arrears")
        private String customerArrears;

    }
}
