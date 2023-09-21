package com.decagon.eventhubbe.domain.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "bookings")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Booking {
    @Id
    private String id;

    @Indexed(unique = true)
    private BigDecimal amount;
    private Integer qty;

    @DBRef
    private EventTicket eventTicket;
    @DBRef
    private Event event;
    @DBRef
    private AppUser appUser;
}
