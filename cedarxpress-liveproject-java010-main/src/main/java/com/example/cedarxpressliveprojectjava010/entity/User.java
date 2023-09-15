package com.example.cedarxpressliveprojectjava010.entity;

import com.example.cedarxpressliveprojectjava010.enums.Gender;
import com.example.cedarxpressliveprojectjava010.enums.Role;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class User extends Base{

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    private LocalDate dob;
    private Gender gender;


    private boolean enabled;
    private boolean locked;
    private boolean accountExpired;
    private boolean credentialsExpired;


    @Enumerated(EnumType.STRING)
    private Role role;


}
