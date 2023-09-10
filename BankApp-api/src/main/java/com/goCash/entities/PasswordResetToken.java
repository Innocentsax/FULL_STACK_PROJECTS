package com.goCash.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;



@Getter
@Setter
@Entity
@NoArgsConstructor
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tokenId;
    private String token;
    private LocalDateTime expirationTime;
    private static final int EXPIRATION_TIME = 5;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public PasswordResetToken(String token, User user) {
        super();
        this.token = token;
        this.user = user;
        this.expirationTime = this.getTokenExpirationTime();
    }
    public LocalDateTime getTokenExpirationTime() {
        return LocalDateTime.now().plusMinutes(EXPIRATION_TIME);
    }
}