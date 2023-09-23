package com.decagon.fitnessoapp.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull
    @Column(nullable = false)
    private String password;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ROLE_DETAIL roleDetail;

    @Column(name = "verify_email", nullable = false)
    private boolean verifyEmail = false;

    @NotNull
    @Column(nullable = false)
    private String gender;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfBirth;

    private String image;

    private String resetPasswordToken;

    public Person(Long id, String email, String userName, String firstName, String lastName, String token) {
    }
}
