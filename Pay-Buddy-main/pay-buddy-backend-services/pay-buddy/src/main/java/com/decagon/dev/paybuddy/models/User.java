package com.decagon.dev.paybuddy.models;

import com.decagon.dev.paybuddy.enums.Gender;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "user_table")
public class User
{

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String firstName;
    private String lastName;
    private String otherName;
    private String uuid;
    @OneToOne
    @JoinColumn(name = "address",referencedColumnName = "addressId")
    private Address address;
    private String email;
    private String password;
    private Integer userBvn;
    private LocalDate dateOfBirth;
    private Gender gender;
    private Boolean isEmailVerified = false;
    private Boolean isBvnVerified = false;
    private Boolean isLocked = false;
    private String confirmationToken;
    private String phoneNumber;
    private int loginCount;

    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
    private Collection<Role> roles;


}
