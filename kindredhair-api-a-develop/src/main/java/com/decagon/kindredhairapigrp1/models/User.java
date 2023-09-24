package com.decagon.kindredhairapigrp1.models;


import com.decagon.kindredhairapigrp1.models.Basemodel.BaseModel;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class User extends BaseModel {

    @NotBlank(message = "Email field  cannot be empty")
    @Email
    @Column(unique = true)
    private String email;
    @NotBlank(message = "Password field  cannot be empty")
    private String password;
    private Boolean enabled  =  false;
    @NotBlank(message = "Status field  cannot be empty")
    private String status;
    @NotBlank(message = "Role field  cannot be empty")
    private String role;
    @NotBlank(message = "Phone Number field  cannot be empty")
    private String phoneNumber;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserDetails userDetails;
    @UpdateTimestamp
    private Timestamp lastLogin;

  }
