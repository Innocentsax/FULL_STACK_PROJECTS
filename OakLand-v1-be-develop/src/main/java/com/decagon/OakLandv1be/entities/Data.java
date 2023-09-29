package com.decagon.OakLandv1be.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public  class Data {
        private BigDecimal amount;
        private String currency;
        private String transaction_date;
        private String status;
        private String reference;
        private String domain;
        // private String metadata;
        private String gateway_response;
        private String message;
        private String channel;
        private String ip_address;
        private String fees;
        private String plan;
        private String paid_at;
    }

