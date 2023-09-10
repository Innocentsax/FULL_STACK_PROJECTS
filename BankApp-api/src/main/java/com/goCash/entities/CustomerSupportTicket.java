package com.goCash.entities;

import com.goCash.enums.TicketStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "support_tickets")
public class CustomerSupportTicket extends BaseEntity{


    @Column(name = "issue_description", nullable = false)
    private String issueDescription;

    @Column(name = "submission_date", nullable = false)
    private LocalDateTime submissionDate;

    @Column(nullable = false)
    private TicketStatus ticketStatus;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
