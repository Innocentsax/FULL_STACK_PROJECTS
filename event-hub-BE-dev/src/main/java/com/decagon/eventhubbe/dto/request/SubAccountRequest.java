package com.decagon.eventhubbe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubAccountRequest {
        private String business_name;
        private String bank_code;
        private String account_number;
        private String percentage_charge;
}
