package com.example.hive.entity;
import com.example.hive.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID user_id;
    @Column(name = "full_name")
    private String fullName;

    @Column(unique = true)
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "valid_id")
    private String validId;
    private String address;
    private String password;
    private Boolean isVerified = false;

    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(targetEntity = Task.class, fetch = FetchType.EAGER)
    private List<Task> task;

    @OneToOne(fetch = FetchType.EAGER)
    private Wallet wallet;


    @OneToMany(mappedBy = "user")
    private List<Notification> notificationList = new ArrayList<>();


    public void addRole(Role role) {
        this.role.getRole();
    }

    public Set<Role> getRoles() {
        return Collections.singleton(role);
    }

}
