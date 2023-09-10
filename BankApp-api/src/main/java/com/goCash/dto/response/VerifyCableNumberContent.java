package com.goCash.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class VerifyCableNumberContent {
    @JsonProperty("Customer_Name")
    private String customerName;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("Due_Date")
    private String dueDate;

    @JsonProperty("Customer_Number")
    private long customerNumber;

    @JsonProperty("Customer_Type")
    private String customerType;

    @JsonProperty("Current_Bouquet")
    private String currentBouquet;

    @JsonProperty("Current_Bouquet_Code")
    private String currentBouquetCode;

    @JsonProperty("Renewal_Amount")
    private Integer renewalAmount;
}
