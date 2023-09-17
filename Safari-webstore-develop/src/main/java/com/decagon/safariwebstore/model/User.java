package com.decagon.safariwebstore.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseModel{

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private  String email;

    private String gender;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    private String password;

    @Column(name = "password_reset_token", columnDefinition = "TEXT")
    private String passwordResetToken;

    @Column(name = "password_reset_expire_date")
    private String passwordResetExpireDate;

    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @ManyToMany
    @JoinTable(name = "favorite_products",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )

    private List<Product> favouriteProducts;

    public User(String firstName, String lastName, String email, String gender, String dateOfBirth, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
    }

}
