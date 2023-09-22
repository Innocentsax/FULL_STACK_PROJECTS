package com.decagon.fintechpaymentapisqd11a.models;

import com.decagon.fintechpaymentapisqd11a.enums.UserStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_tbl")
public class Users extends BaseClass{

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(length = 11, unique = true, nullable = false)
    @Size(min = 11, max = 11, message = "BVN must be 11 characters")
    private String bvn;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Column(length = 11)
    @Size(min = 11, max = 11, message = "Phone number must be 11 digits")
    private String phoneNumber;

    @Column(nullable = false)
    @Size(min = 8, message = "Password must not be less than 8 characters")
    private String password;

   // @Column(length = 6)
    @Size(min = 6, message = "Transaction pin must be 6 characters")
    private String transactionPin;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus = UserStatus.INACTIVE;

    @OneToOne(mappedBy = "users")
    private Wallet wallet;

    @OneToOne(mappedBy = "users")
    private Transaction transaction;

    private String role;
}
