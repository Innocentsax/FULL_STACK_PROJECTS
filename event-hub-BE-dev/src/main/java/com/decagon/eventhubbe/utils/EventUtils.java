package com.decagon.eventhubbe.utils;

import com.decagon.eventhubbe.domain.entities.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class EventUtils {

    public static boolean eventValidation(Event event){
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        LocalDate eventDate = DateUtils.getLocalDate(event.getStartDate());
        LocalTime eventEndTime = DateUtils.getLocalTime(event.getEndTime());

        if(event.isDeleted()){
            return false;
        }
        return (eventDate.isEqual(currentDate) && currentTime.minusHours(2).isBefore(eventEndTime))
                || eventDate.isAfter(currentDate);
    }
}
