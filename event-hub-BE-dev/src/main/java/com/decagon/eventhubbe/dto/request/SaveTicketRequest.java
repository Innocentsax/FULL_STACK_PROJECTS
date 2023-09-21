package com.decagon.eventhubbe.dto.request;

import com.decagon.eventhubbe.dto.response.EventTicketResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SaveTicketRequest {
    private String eventId;
    private List<EventTicketResponse> eventTicketList;
}
