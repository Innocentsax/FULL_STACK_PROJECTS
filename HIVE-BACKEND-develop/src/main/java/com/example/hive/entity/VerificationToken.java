package com.example.hive.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;
import java.util.Date;


    @Getter
    @Setter
    @ToString
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Entity
    @Table(name = "verification_tokens")
    public class VerificationToken {
        private static final int EXPIRATION_TIME = 10;
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String token;
        private Date expirationTime;

        @OneToOne(fetch = FetchType.EAGER)
        @JoinColumn(
                name = "users_id",
                updatable = false,
                foreignKey = @ForeignKey(name= "FK_USER_VERIFY_TOKEN")
        )
        private User user;

        public VerificationToken(String token) {
            super();
            this.token = token;
            this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
        }

        public VerificationToken(String token, User user) {
            super();
            this.token = token;
            this.user = user;
            this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
        }

        private Date calculateExpirationDate(int expirationTime) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(new Date().getTime());
            calendar.add(Calendar.MINUTE, expirationTime);
            return new Date(calendar.getTime().getTime());
        }
    }


