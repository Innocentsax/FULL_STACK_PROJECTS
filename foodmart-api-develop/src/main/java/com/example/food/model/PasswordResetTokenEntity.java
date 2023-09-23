package com.example.food.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "password_reset_tokens")
@Entity
@Setter
@Getter
public class PasswordResetTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String token;
    @OneToOne
    @JoinColumn(name = "users_details_users_id")
    private Users usersDetails;

}
