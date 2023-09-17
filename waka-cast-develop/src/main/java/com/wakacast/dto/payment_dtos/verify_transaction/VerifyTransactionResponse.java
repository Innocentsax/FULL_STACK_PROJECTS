package com.wakacast.dto.payment_dtos.verify_transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by SQ-OGBE PC on 21/09/2017.
 * the response returned from the paystack verification
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifyTransactionResponse {

    /**
     * this status is "true" if the request is successful and false if is not
     * NOTE: This does not mean the transaction was successful, data.status holds that information
     */
    private String status;
    /**
     * information about the request, could be "verification successful" or "invalid key"
     */
    private String message;
    /**
     * contains details about the transaction
     */
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}