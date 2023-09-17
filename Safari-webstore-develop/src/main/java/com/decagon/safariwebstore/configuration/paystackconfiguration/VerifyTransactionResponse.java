package com.decagon.safariwebstore.configuration.paystackconfiguration;


import com.decagon.safariwebstore.payload.response.PaystackDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * the response returned from the paystack verification
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifyTransactionResponse {


    private String status;

    private String message;

    private PaystackDTO data;

    public VerifyTransactionResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PaystackDTO getData() {
        return data;
    }

    public void setData(PaystackDTO data) {
        this.data = data;
    }

}
