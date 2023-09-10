package com.goCash.entities;

import com.goCash.enums.Gender;
import com.goCash.enums.Roles;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class User extends BaseEntity{


    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Column
    private String address;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    
    @Column(name = "roles", nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles role;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate;

    private boolean isVerified;

    @OneToMany(mappedBy ="user")
    private List<AirtimeData> airtimeData;

    @OneToOne(mappedBy ="user")
    private WalletAccount walletAccount;



}
