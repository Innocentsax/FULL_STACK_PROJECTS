package com.goCash.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter

public class AccountData {
    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("account_name")
    private String accountName;
}