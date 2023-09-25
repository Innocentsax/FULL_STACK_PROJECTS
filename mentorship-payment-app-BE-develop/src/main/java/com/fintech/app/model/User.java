package com.fintech.app.model;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotEmpty
    @Size(min = 3, message = "First name can not be less than 3")
    private String firstName;

    @NotEmpty
    @Size(min = 3, message = "Last name can not be less than 3")
    private String lastName;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 10, message = "Phone number should have at least 10 characters")
    private String phoneNumber;

    @NotEmpty
    @Size(min = 11, max = 11, message = "BVN number must not be less than 10")
    private String bvn;

    @Size(min = 4, message = "Pin can not be more than 4")
    private String pin;

    @NotEmpty
    @Size(min = 6, message = "Password should have at least 6 characters")
    private String password;

    private boolean status;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifyAt;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

}
