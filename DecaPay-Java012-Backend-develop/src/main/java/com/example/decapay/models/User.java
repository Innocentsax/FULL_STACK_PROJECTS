package com.example.decapay.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * @author Ikechi Ucheagwu
 * @created 09/12/2022 - 5:00 PM
 * @project DECAPAY
 */

@Getter
@Setter
@ToString
@Entity
@Table(name = "user_tb")
public class User extends BaseEntity {

    @Column(unique = true)
    private String userId;
    private String firstName;
    private String lastName;
    private boolean isVerified;
    private String imagePath;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BudgetCategory> categories;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Budget> budgets;
}
