package com.easyLend.userservice.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "ver_Email_link")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class VerificationEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private Date expiresAt;
    @OneToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    public VerificationEmail(String token, AppUser appUser) {
        this.token = token;
        this.user = appUser;
        this.expiresAt = getExpirationDate();
    }

    private Date getExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, 5);
        return new Date(calendar.getTime().getTime());

    }
}
