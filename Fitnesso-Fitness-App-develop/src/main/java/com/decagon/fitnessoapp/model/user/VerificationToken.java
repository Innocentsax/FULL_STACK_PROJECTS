package com.decagon.fitnessoapp.model.user;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class VerificationToken {

    @SequenceGenerator(
            name = "verification_token_sequence",
            sequenceName = "verification_token_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "verification_token_sequence"
    )
    private Long id;

    @Column(name = "token_code", nullable = false)
    private String tokenCode;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    private Person person;

    public VerificationToken(String tokenCode, LocalDateTime createdAt, LocalDateTime expiresAt, Person person) {
        this.tokenCode = tokenCode;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.person = person;
    }
}
