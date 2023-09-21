package com.decagon.eventhubbe.domain.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "payments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Payment {

    @Id
    private String id;

    @Indexed(unique = true)
    private BigDecimal amount;

    private String purchaseDate;

    private String buyerEmail;
    private String buyerName;
    private Integer qty;

    @DBRef
    private EventTicket eventTicket;
    @DBRef
    private Event event;
}
