package com.decagon.eventhubbe.exception;

public class TicketNotFoundException extends RuntimeException{
    public TicketNotFoundException(String ticketId) {
        super("Ticket with id: "+ticketId+" does not exist");
    }
}
