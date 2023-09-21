package com.decagon.eventhubbe.dto.response;

import com.decagon.eventhubbe.domain.entities.geoLocation.GeoLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventResponse implements Serializable {
    private String id;
    private String title;
    private String caption;
    private String description;
    private String bannerUrl;
    private String organizer;
    private String category;
    private GeoLocation coordinates;
    private String location;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private boolean isExpired;
    private boolean isActive;
    private List<EventTicketResponse> tickets;
    private UserResponse appUser;
}
