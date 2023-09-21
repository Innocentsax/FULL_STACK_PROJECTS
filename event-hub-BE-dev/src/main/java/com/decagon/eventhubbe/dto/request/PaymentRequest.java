package com.decagon.eventhubbe.dto.request;

import com.decagon.eventhubbe.domain.entities.EventTicket;
import com.decagon.eventhubbe.dto.response.EventTicketResponse;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PaymentRequest {
    private String email;
    private String buyerEmail;
    private String buyerName;
    private String eventId;
    private List<EventTicketResponse> eventTicketList;

}
