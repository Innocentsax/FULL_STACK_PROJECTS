package com.decagon.eventhubbe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventTicketRequest {
    private String id;
    private String ticketClass;
    private String description;
    private Double ticketPrice;
    private Integer quantity;
}
