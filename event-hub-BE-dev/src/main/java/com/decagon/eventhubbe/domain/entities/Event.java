package com.decagon.eventhubbe.domain.entities;

import com.decagon.eventhubbe.ENUM.EventCategory;
import com.decagon.eventhubbe.domain.entities.geoLocation.GeoLocation;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "events")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
//@CompoundIndexes({
//        @CompoundIndex(name = "event_text_index", def = "{'title': 'text', 'description': 'text', 'caption': 'text'}, default_language='english', weights = {'title': 3, 'description': 2, 'caption': 1}")
//})
public class Event {

    @Id
    private String id;
    @Indexed(name = "title_index")
    private String title;
    @Indexed(name = "caption_index")
    private String caption;
    @Indexed(name = "description_index")
    private String description;
    private String organizer;
    @Field("category")
    private EventCategory category;
    private GeoLocation coordinates;
    @Indexed(name = "location_index")
    private String location;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private String bannerUrl;
    private String createdAt;
    private LocalDateTime date;
    private boolean isDeleted;
    private boolean isExpired;
    private boolean isActive;

    @DBRef
    private AppUser appUser;

    @DBRef
    private List<EventTicket> eventTickets;

}
