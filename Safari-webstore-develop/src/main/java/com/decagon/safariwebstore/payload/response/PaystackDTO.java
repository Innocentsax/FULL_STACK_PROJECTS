package com.decagon.safariwebstore.payload.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.tomcat.util.http.parser.Authorization;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaystackDTO {

    private BigDecimal amount;

    private String currency;

    private String transaction_date;

    private String status;

    private String reference;

    private String domain;

    private String gateway_response;

    private String message;

    private String channel;

    private String ip_address;

    private String fees;

    private String plan;

    private String paid_at;

    private Authorization authorization;

}

