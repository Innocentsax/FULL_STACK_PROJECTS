package com.decagon.eventhubbe.dto.response;

import lombok.Data;

@Data
public class  PaymentResponse {
    private EventTicketResponse data;
    private String message;

}
