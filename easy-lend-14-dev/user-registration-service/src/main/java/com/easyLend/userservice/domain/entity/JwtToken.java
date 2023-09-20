package com.easyLend.userservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Table(name ="jwt")
@Entity
@Builder
@Getter
@Setter
public class JwtToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tokenId;

    private String accessToken;

    private String refreshToken;

    private Date generatedAt;
    private Date expiresAt;
    private boolean isRevoked;
    private boolean expired;
    @OneToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

}
