package com.easyLend.userservice.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "lenders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lender {
    @Id
    @GeneratedValue
    @Column(name = "lender_id")
    private Long lenderId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

