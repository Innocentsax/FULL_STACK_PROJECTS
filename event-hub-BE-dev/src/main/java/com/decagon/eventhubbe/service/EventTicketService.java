package com.decagon.eventhubbe.service;

import com.decagon.eventhubbe.domain.entities.EventTicket;
import com.decagon.eventhubbe.dto.request.EventTicketRequest;
import com.decagon.eventhubbe.dto.request.SaveTicketRequest;
import com.decagon.eventhubbe.dto.response.EventTicketResponse;
import com.decagon.eventhubbe.dto.response.SaveTicketResponse;
import com.decagon.eventhubbe.dto.response.TicketsSalesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface EventTicketService {

    List<TicketsSalesResponse> trackTicketSales(String eventId);

    String saveTicket(SaveTicketRequest request);

    List<SaveTicketResponse> getSavedTickets();
}
