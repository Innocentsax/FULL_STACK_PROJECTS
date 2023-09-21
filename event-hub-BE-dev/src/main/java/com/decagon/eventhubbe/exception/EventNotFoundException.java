package com.decagon.eventhubbe.exception;

public class EventNotFoundException extends RuntimeException{
    public EventNotFoundException(String id) {
        super("Event with id : "+id+" does not exist");
    }
}
