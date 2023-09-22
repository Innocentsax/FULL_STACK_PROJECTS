package com.decagon.fintechpaymentapisqd11a.token;


import com.decagon.fintechpaymentapisqd11a.models.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ConfirmationToken {


    @Id
    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"
    )

    private java.lang.Long id;

    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private Users users;

    public ConfirmationToken(String token, LocalDateTime createdAt,
                             LocalDateTime expiresAt,  Users users) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.users = users;
    }

}
