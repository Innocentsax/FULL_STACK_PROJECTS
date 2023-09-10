package com.goCash.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.goCash.dto.request.Meta;
import lombok.Data;

import java.util.Date;

@Data
public class TransferData {
    @JsonProperty("id")
    private  int id;
    @JsonProperty("account_number")
    private String accountNumber;
    @JsonProperty("bank_code")
    private String bankCode;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("debit_currency")
    private String destinationAccountNumber;
    @JsonProperty("amount")
    private int amount;
    @JsonProperty("fee")
    private int fee;
    @JsonProperty("status")
    private String status;
    @JsonProperty("reference")
    private String reference;
    @JsonProperty("meta")
    private Meta meta;
    @JsonProperty("narration")
    private String narration;
    @JsonProperty("complete_message")
    private String completeMessage;
    @JsonProperty("requires_approval")
    private int requiresApproval;
    @JsonProperty("is_approved")
    private int isApproved;
    @JsonProperty("bank_name")
    private String bankName;

}
