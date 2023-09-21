package com.decagon.eventhubbe.service.impl;

import com.decagon.eventhubbe.domain.entities.*;
import com.decagon.eventhubbe.domain.repository.BookingRepository;
import com.decagon.eventhubbe.domain.repository.EventRepository;
import com.decagon.eventhubbe.domain.repository.EventTicketRepository;
import com.decagon.eventhubbe.domain.repository.PaymentRepository;
import com.decagon.eventhubbe.dto.request.SaveTicketRequest;
import com.decagon.eventhubbe.dto.response.EventResponse;
import com.decagon.eventhubbe.dto.response.EventTicketResponse;
import com.decagon.eventhubbe.dto.response.SaveTicketResponse;
import com.decagon.eventhubbe.dto.response.TicketsSalesResponse;
import com.decagon.eventhubbe.exception.EventNotFoundException;
import com.decagon.eventhubbe.exception.UnauthorizedException;
import com.decagon.eventhubbe.service.EventTicketService;
import com.decagon.eventhubbe.utils.EventUtils;
import com.decagon.eventhubbe.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventTicketServiceImpl implements EventTicketService {
    private final AppUserServiceImpl appUserService;
    private final EventRepository eventRepository;
    private final EventTicketRepository eventTicketRepository;
    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<TicketsSalesResponse> trackTicketSales(String eventId){
        String email = UserUtils.getUserEmailFromContext();
        AppUser appUser = appUserService.getUserByEmail(email);
        Event event = eventRepository.findById(eventId)
                .orElseThrow(()-> new EventNotFoundException(eventId));
        if(!event.getAppUser().getId().equals(appUser.getId())){
            throw new UnauthorizedException("Event Created By Another User");
        }
        List<TicketsSalesResponse> ticketsSalesResponses = new ArrayList<>();
        Map<String, TicketsSalesResponse> responseMap = new HashMap<>();

        List<Payment> payments = paymentRepository.findAllByEvent(event);

        for (Payment payment : payments) {
            String eventTicketId = payment.getEventTicket().getId();

            TicketsSalesResponse ticketsSalesResponse = responseMap.get(eventTicketId);
            if (ticketsSalesResponse == null) {
                ticketsSalesResponse = new TicketsSalesResponse();
                ticketsSalesResponse.setEventTicketResponse(EventTicketResponse.builder()
                        .ticketClass(payment.getEventTicket().getTicketClass())
                        .ticketPrice(payment.getEventTicket().getTicketPrice())
                        .id(payment.getEventTicket().getId())
                        .description(payment.getEventTicket().getDescription())
                        .quantity(payment.getEventTicket().getQuantity())
                        .build());
                responseMap.put(eventTicketId, ticketsSalesResponse);
            }
            if(ticketsSalesResponse.getAmount() == null && ticketsSalesResponse.getQuantitySold() == null){
                ticketsSalesResponse.setAmount(payment.getAmount());
                ticketsSalesResponse.setQuantitySold(payment.getQty());
            }else{
                assert ticketsSalesResponse.getAmount() != null;
                ticketsSalesResponse.setAmount(ticketsSalesResponse.getAmount().add(payment.getAmount()));
                ticketsSalesResponse.setQuantitySold(ticketsSalesResponse.getQuantitySold() + payment.getQty());
            }
        }
        for (String eventTicketId : responseMap.keySet()) {
            TicketsSalesResponse ticketsSalesResponse = responseMap.get(eventTicketId);
          ticketsSalesResponses.add(ticketsSalesResponse);
        }
        return ticketsSalesResponses;
    }

    @Override
    public String saveTicket(SaveTicketRequest request){
        String email = UserUtils.getUserEmailFromContext();
        AppUser appUser = appUserService.getUserByEmail(email);
        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(()-> new EventNotFoundException(request.getEventId()));
        List<EventTicketResponse> eventTicketResponses = request.getEventTicketList();
        for(EventTicketResponse eventTicketResponse : eventTicketResponses) {
            EventTicket eventTicket = eventTicketRepository.findById(eventTicketResponse.getId())
                    .orElseThrow(() -> new EventNotFoundException(eventTicketResponse.getId()));
            if (!eventTicket.getEvent().equals(event)) {
                throw new IllegalArgumentException("Wrong Event Tickets Entered");
            }
            bookingRepository.save(Booking.builder()
                    .qty(eventTicketResponse.getQuantity())
                    .amount(BigDecimal.valueOf((long) eventTicketResponse.getQuantity() * eventTicketResponse.getQuantity()))
                    .appUser(appUser)
                    .event(event)
                    .eventTicket(eventTicket)
                    .build());
        }
        return "Ticket(s) Saved Successfully";
    }

    @Override
    public List<SaveTicketResponse> getSavedTickets(){
        List<SaveTicketResponse> saveTicketResponses = new ArrayList<>();
        String email = UserUtils.getUserEmailFromContext();
        AppUser appUser = appUserService.getUserByEmail(email);
        List<Booking> bookings = bookingRepository.findAllByAppUser(appUser);
        if(!bookings.isEmpty()){
            for(Booking booking : bookings){
                EventTicket eventTicket = booking.getEventTicket();
                Event event = booking.getEvent();
                if(EventUtils.eventValidation(event)){
                    SaveTicketResponse response = SaveTicketResponse.builder()
                            .amount(booking.getAmount())
                            .qty(booking.getQty())
                            .event(modelMapper.map(event, EventResponse.class))
                            .eventTicket(modelMapper.map(eventTicket, EventTicketResponse.class))
                            .soldOut(validateTicket(eventTicket,booking))
                            .build();
                    saveTicketResponses.add(response);
                }
            }
            return saveTicketResponses;
        }
        return null;
    }

    private boolean validateTicket(EventTicket eventTicket, Booking booking){
        return booking.getQty() > eventTicket.getQuantity();
    }
}
