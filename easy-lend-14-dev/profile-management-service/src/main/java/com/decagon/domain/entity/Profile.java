package com.decagon.domain.entity;

import com.decagon.domain.screen.converter.JsonConverter;
import com.decagon.domain.constant.ProfileStatus;
import com.decagon.domain.screen.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;

    @Column(name = "contactInformation")
    private String contactInformation;

    @Column(name = "employmentStatus")
    private String employmentStatus;

    @Column(name = "governmentId")
    private String governmentId;

    @Column(name = "incomeStatus")
    private String incomeStatus;

    @Column(name = "proofOfAddress")
    private String proofOfAddress;

    @Column(name = "bankAccount")
    private String bankAccount;
}
