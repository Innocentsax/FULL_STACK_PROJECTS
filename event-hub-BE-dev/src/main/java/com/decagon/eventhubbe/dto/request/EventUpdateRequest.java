package com.decagon.eventhubbe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventUpdateRequest {
    private String title;
    private String caption;
    private String description;
    private String organizer;
    private String category;
    private String location;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
}
