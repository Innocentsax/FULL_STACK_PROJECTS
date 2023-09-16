package com.decagon.chompapp.models;

import com.decagon.chompapp.enums.Gender;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor @Builder
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"}),  @UniqueConstraint(columnNames = {"email"})})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Size(min = 2, max=64, message = "firstname must be at least two letter long")
    @Column(nullable = false)
    private String firstName;

    @Size(min = 2, max=64, message = "lastname must be at least two letter long")
    @Column(nullable = false)
    private String lastName;


    @Size(min = 2, max=64, message = "lastname must be at least two letter long")
    @Column(nullable = false)
    private String username;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;

    @Size(min = 8, max = 16, message = "password must be at least 8 letters long")
    private String password;

    @DateTimeFormat(pattern = "dd-mm-yyyy")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "roleId"))
    private Set<Role> roles;



    @OneToOne(mappedBy = "user", orphanRemoval = true)
    private Wallet wallet;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Address> addressBook;


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
    cascade = CascadeType.ALL)
    private List<Order> orders;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
    cascade = CascadeType.ALL)
    private List<Favorites> favourites;

    @OneToOne(mappedBy = "user")
    private Cart cart;

    @Size(min = 30, max = 400)
    private String confirmationToken;

    private Boolean isEnabled;

    private Boolean isActive;

}
