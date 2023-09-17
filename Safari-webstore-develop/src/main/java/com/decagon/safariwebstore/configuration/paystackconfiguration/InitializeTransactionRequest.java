package com.decagon.safariwebstore.configuration.paystackconfiguration;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.validation.constraints.Digits;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InitializeTransactionRequest {

    @Digits(integer = 9, fraction = 0)
    private Integer amount;

    private String email;

    private String reference;

    private String callback_url;

    private Float quantity;



}

