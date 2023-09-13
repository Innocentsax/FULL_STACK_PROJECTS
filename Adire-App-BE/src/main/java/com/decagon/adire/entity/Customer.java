package com.decagon.adire.entity;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private  String address;

    public Customer(String firstName, String lastName, String email, String phoneNumber, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private Set<Order> orders = new HashSet<>();
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private Set<Receipt> receipts = new HashSet<>();
    @ManyToOne()
    @JoinColumn(name = "userId" , referencedColumnName = "id")
    private Designer designer;

}
