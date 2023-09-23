package com.example.hive.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ValidateAccountResponse {
    @JsonProperty("accountNumber")
    String account_number;
    @JsonProperty("accountName")
    String account_name;
    @JsonProperty("bankCode")
    String bank_code;
    @JsonProperty("bankName")
    String bank_name;
}
